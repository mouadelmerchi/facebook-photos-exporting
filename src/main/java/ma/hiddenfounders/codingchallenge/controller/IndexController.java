package ma.hiddenfounders.codingchallenge.controller;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.Photo;
import org.springframework.social.facebook.api.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;
import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;
import ma.hiddenfounders.codingchallenge.service.FacebookAlbumService;
import ma.hiddenfounders.codingchallenge.service.FacebookPhotoService;

@Controller
public class IndexController {

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

   @Value("${app.facebook.images.path}")
   private String facebookAlbumsPath;

   @Value("${app.facebook.images.extension}")
   private String facebookImagesExt;
   
   @Value("${app.facebook.defaultPageSize}")
   private Integer defaultPageSize;
   
   @RequestMapping(value = "/", method = RequestMethod.GET)
   public String getIndexPage() {
      importAlbumsFromFacebook();
      return "index";
   }

   private void importAlbumsFromFacebook() {
      if (connectionRepository.findPrimaryConnection(Facebook.class) != null) {

         long nbAlbums = fbAlbumService.countAllFacebookAlbums();

         // Check to see whether albums importing is necessary
         if (nbAlbums == 0) {
            final MediaOperations mediaOps = facebook.mediaOperations();
            
            // a consumer function that saves albums to mongodb
            Consumer<Album> albumOp = (album) -> {
               FacebookAlbum fbAlbum = new FacebookAlbum(album.getId(), album.getName(), album.getCount(),
                     album.getCoverPhoto(), album.getType());

               // save the current album to its mongodb collection
               fbAlbumService.saveFacebookAlbum(fbAlbum);

               // an inner consumer function that saves albums photos to mongodb
               // images are saved to disc for performance purposes
               Consumer<Photo> photoOp = (photo) -> {
                  byte[] photoBytes = facebook.mediaOperations().getPhotoImage(photo.getId());
                  String realAlbumsPath = context.getRealPath(facebookAlbumsPath);
                  FacebookPhoto fbPhoto = new FacebookPhoto(photo.getId(), photo.getName(),
                        new Reference(album.getId(), album.getName()), photoBytes, photo.getCreatedTime(),
                        photo.getWidth(), photo.getHeight(), realAlbumsPath, facebookImagesExt);
                  try {
                     // save photo image to disc then persist photo object to its
                     // mongodb collection
                     fbPhotoService.saveFacebookPhoto(fbPhoto);
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               };

               importMediaItemsFromFacebook(album.getId(), new PagingParameters(defaultPageSize, 0, null, null), mediaOps::getPhotos,
                     photoOp);
            };

            importMediaItemsFromFacebook("me", new PagingParameters(defaultPageSize, 0, null, null), mediaOps::getAlbums, albumOp);
         }
      }
   }

   // method that iterates over a list of albums/photos and executes
   // the provided consumer function passing it the current album/photo.
   // all list pages are processed using PagingParameters
   private <T> void importMediaItemsFromFacebook(String id, PagingParameters pagingParams,
         BiFunction<String, PagingParameters, PagedList<T>> mediaOp, Consumer<T> consumer) {

      PagedList<T> list = mediaOp.apply(id, pagingParams);
      while (!list.isEmpty()) {
         list.forEach(item -> {
            consumer.accept(item);
         });

         pagingParams = list.getNextPage();
         if (pagingParams != null) {
            list = mediaOp.apply(id, pagingParams);
         } else {
            list.clear();
         }
      }
   }
}