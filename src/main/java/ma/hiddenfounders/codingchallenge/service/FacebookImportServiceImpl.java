package ma.hiddenfounders.codingchallenge.service;

import java.io.IOException;
import java.util.function.BiFunction;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.MediaOperations;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.social.facebook.api.PagingParameters;
import org.springframework.social.facebook.api.Photo;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.codingchallenge.common.util.Utils;
import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;
import ma.hiddenfounders.codingchallenge.entity.FacebookAlbumReference;
import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;

@Service
class FacebookImportServiceImpl implements FacebookImportService {

   @Autowired
   private Facebook facebook;

   @Autowired
   private FacebookAlbumService fbAlbumService;

   @Autowired
   private FacebookPhotoService fbPhotoService;

   @Override
   public void importFacebookAlbums(String userEmail, String albumsPath, Integer defaultPageSize, String imagesExt) {
      if (userEmail != null) {
         long nbAlbums = fbAlbumService.countFacebookAlbumsByOwner(userEmail);

         // Check to see whether albums importing is necessary
         if (nbAlbums == 0) {
            final MediaOperations mediaOps = facebook.mediaOperations();

            // a consumer function that saves albums to mongodb
            Consumer<Album> albumOp = (album) -> {
               FacebookAlbum fbAlbum = new FacebookAlbum(album.getId(), album.getName(), album.getCount(),
                     album.getCoverPhoto(), album.getType(), userEmail);

               // save the current album to its mongodb collection
               fbAlbumService.saveFacebookAlbum(fbAlbum);

               // an inner consumer function that saves albums photos to
               // mongodb
               // images are saved to disc for performance purposes
               Consumer<Photo> photoOp = (photo) -> {
                  byte[] photoBytes = mediaOps.getPhotoImage(photo.getId());
                  FacebookPhoto fbPhoto = new FacebookPhoto(photo.getId(), photo.getName(),
                        new FacebookAlbumReference(album.getId(), album.getName(), userEmail), photoBytes,
                        photo.getCreatedTime(), photo.getWidth(), photo.getHeight(), Utils.generateImageKey(),
                        imagesExt);
                  try {
                     // save photo image to disc then persist photo object to
                     // its mongodb collection
                     if (fbPhotoService.savePhotoImageToDisc(albumsPath, fbPhoto)) {
                        fbPhotoService.saveFacebookPhoto(fbPhoto);
                     }
                  } catch (IOException e) {
                     e.printStackTrace();
                  }
               };

               importMediaItemsFromFacebook(album.getId(), new PagingParameters(defaultPageSize, 0, null, null),
                     mediaOps::getPhotos, photoOp);
            };

            importMediaItemsFromFacebook("me", new PagingParameters(defaultPageSize, 0, null, null),
                  mediaOps::getAlbums, albumOp);
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