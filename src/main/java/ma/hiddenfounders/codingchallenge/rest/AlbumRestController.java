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
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import ma.hiddenfounders.codingchallenge.common.util.CurrentUser;
import ma.hiddenfounders.codingchallenge.dto.AlbumsListResponse;
import ma.hiddenfounders.codingchallenge.dto.FacebookAlbumDTO;
import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;
import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;
import ma.hiddenfounders.codingchallenge.service.FacebookAlbumService;
import ma.hiddenfounders.codingchallenge.service.FacebookPhotoService;

@RestController
@RequestMapping("/api/facebook")
public class AlbumRestController {
   
   private static final Logger LOGGER = LoggerFactory.getLogger(AlbumRestController.class);

   @Value("${app.facebook.images.path}")
   private String facebookAlbumsPath;

   @Value("${app.facebook.albums.pageSize}")
   private Integer albumsPageSize;

   @Autowired
   private ConnectionRepository connectionRepository;
   
   @Autowired
   private Facebook facebook;
   
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

   @RequestMapping(value = "/albums", method = RequestMethod.GET)
   @ResponseBody
   public ResponseEntity<AlbumsListResponse> fetchUserAlbums(@CurrentUser UserDetails user) {
      
      LOGGER.info("######### Current User {} ############", user);
      
      int currentPage = 0;
      String email = user.getUsername();
      FacebookAlbum probe = new FacebookAlbum();
      probe.setOwner(email);
      List<FacebookAlbumDTO> albumsPage = fbAlbumService
            .findUserFacebookAlbums(probe, PageRequest.of(currentPage, albumsPageSize)).stream().map(fbAlbum -> {
               FacebookPhoto fbPhoto = fbPhotoService.getFacebookPhotoById(fbAlbum.getCoverPhoto().getId());
               String coverPhotoUri = MvcUriComponentsBuilder
                     .fromMethodName(AlbumRestController.class, "fetchPhotoResource", fbPhoto.getImageKey()).build()
                     .toString();
               return new FacebookAlbumDTO(fbAlbum.getId(), fbAlbum.getName(), fbAlbum.getCount(), coverPhotoUri,
                     fbAlbum.getType());
            }).collect(Collectors.toList());

      if (albumsPage.isEmpty()) {
         return ResponseEntity.ok(null);
      }

      return ResponseEntity.ok(new AlbumsListResponse(albumsPage, currentPage, albumsPageSize));
   }

   @RequestMapping(value = "/albums/photo/{imageKey:.+}", method = RequestMethod.GET)
   @ResponseBody
   public ResponseEntity<Resource> fetchPhotoResource(@PathVariable String imageKey) {
      FacebookPhoto photo = fbPhotoService.getFacebookPhotoByImageKey(imageKey);
      Resource resource = fbPhotoService.loadPhotoImageFromDisc(realAlbumsPath, photo);
      return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
            String.format("attachment; filename=\"%s\"", resource.getFilename())).body(resource);
   }
}
