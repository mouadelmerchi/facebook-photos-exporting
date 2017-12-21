package ma.hiddenfounders.codingchallenge.rest;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ma.hiddenfounders.codingchallenge.common.util.CurrentUser;
import ma.hiddenfounders.codingchallenge.dto.AlbumsListResponse;
import ma.hiddenfounders.codingchallenge.dto.FacebookAlbumDTO;
import ma.hiddenfounders.codingchallenge.dto.FacebookPhotoDTO;
import ma.hiddenfounders.codingchallenge.dto.PhotosListResponse;
import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;
import ma.hiddenfounders.codingchallenge.entity.FacebookAlbumReference;
import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;
import ma.hiddenfounders.codingchallenge.service.FacebookAlbumService;
import ma.hiddenfounders.codingchallenge.service.FacebookPhotoService;

@RestController
@RequestMapping("/api/facebook")
public class FacebookRestController {

   private static final Logger LOGGER = LoggerFactory.getLogger(FacebookRestController.class);
   
   private static final String THUMBNAIL_SUFFIX = "thumbnail";

   @Value("${app.facebook.images.path}")
   private String facebookAlbumsPath;

   @Value("${app.facebook.pagesToShow}")
   private Integer pagesToShow;
   
   @Value("${app.facebook.albums.pageSize}")
   private Integer albumsPageSize;

   @Value("${app.facebook.photos.pageSize}")
   private Integer photosPageSize;

   @Autowired
   private FacebookAlbumService fbAlbumService;

   @Autowired
   private FacebookPhotoService fbPhotoService;

   @Autowired
   private ServletContext context;

   private String realAlbumsPath;

   @PostConstruct
   public void init() {
      realAlbumsPath = context.getRealPath(facebookAlbumsPath);
   }

   @RequestMapping(value = "/albums/page/{currentPage}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseEntity<AlbumsListResponse> fetchUserAlbums(@CurrentUser UserDetails user, @PathVariable Integer currentPage) {

      String email = user.getUsername();
      FacebookAlbum probe = new FacebookAlbum();
      probe.setOwner(email);
      long totalCount = fbAlbumService.countFacebookAlbumsByOwner(email);
      List<FacebookAlbumDTO> albumsPage = fbAlbumService
            .getUserFacebookAlbums(probe, PageRequest.of(currentPage - 1, albumsPageSize))
            .stream()
            .map(fbAlbum -> {
               FacebookPhoto fbPhoto = fbPhotoService.getFacebookPhotoById(fbAlbum.getCoverPhoto().getId());

               if (fbPhoto == null) {
                  return null;
               }

               String coverPhotoUri = MvcUriComponentsBuilder.fromMethodName(FacebookRestController.class,
                     "fetchPhotoResource", String.format("%s-%s", fbPhoto.getImageKey(), THUMBNAIL_SUFFIX)).build()
                     .toString();
               return new FacebookAlbumDTO(fbAlbum.getId(), fbAlbum.getName(), fbAlbum.getCount(), coverPhotoUri,
                     fbAlbum.getType());
            })
            .filter(fbAlbumDto -> fbAlbumDto != null)
            .collect(Collectors.toList());

      if (albumsPage.isEmpty()) {
         return ResponseEntity.ok(null);
      }

      return ResponseEntity.ok(new AlbumsListResponse(albumsPage, totalCount, albumsPageSize, pagesToShow));
   }

   @RequestMapping(value = "/albums/{albumId}/page/{currentPage}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseEntity<PhotosListResponse> fetchPhotoResources(@PathVariable String albumId, @PathVariable Integer currentPage) {
      
      FacebookAlbum fbAlbum = fbAlbumService.getFacebookAlbumById(albumId);
      if (fbAlbum == null) {
         return ResponseEntity.badRequest().body(null);
      }

      LOGGER.info("########## albumId: {} #########", albumId);
      
      FacebookPhoto probe = new FacebookPhoto();
      probe.setAlbum(new FacebookAlbumReference(albumId));
      List<FacebookPhotoDTO> photosPage = fbPhotoService
            .getFacebookPhotos(probe, PageRequest.of(currentPage - 1, photosPageSize))
            .stream()
            .map(fbPhoto -> {
               if (fbPhoto == null) {
                  return null;
               }
      
               String photoUri = MvcUriComponentsBuilder.fromMethodName(FacebookRestController.class, "fetchPhotoResource",
                     String.format("%s-%s", fbPhoto.getImageKey(), THUMBNAIL_SUFFIX)).build().toString();
      
               return new FacebookPhotoDTO(fbPhoto.getId(), fbPhoto.getName(), photoUri, fbPhoto.getImageKey());
            })
            .collect(Collectors.toList());

      return ResponseEntity.ok(new PhotosListResponse(photosPage, fbAlbum.getName(), fbAlbum.getCount(), photosPageSize, pagesToShow));
   }

   @RequestMapping(value = "/albums/photo/{imageKey:.+}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseEntity<Resource> fetchPhotoResource(@PathVariable String imageKey) {
      boolean isThumb = false;
      if (imageKey != null && imageKey.endsWith(THUMBNAIL_SUFFIX)) {
         isThumb = true;
         imageKey = imageKey.substring(0, imageKey.length() - THUMBNAIL_SUFFIX.length() - 1);
      }

      FacebookPhoto photo = fbPhotoService.getFacebookPhotoByImageKey(imageKey);
      Resource resource = fbPhotoService.loadPhotoImageFromDisc(realAlbumsPath, photo, isThumb);
      return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            String.format("attachment; filename=\"%s\"", resource.getFilename())).body(resource);
   }
}
