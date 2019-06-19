package ma.hiddenfounders.codingchallenge.service;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;

public interface IFacebookPhotoService {

   FacebookPhoto saveFacebookPhoto(FacebookPhoto photo);

   FacebookPhoto getFacebookPhotoById(String id);

   FacebookPhoto getFacebookPhotoByImageKey(String imageKey);

   Page<FacebookPhoto> getFacebookPhotos(FacebookPhoto probe, Pageable pageable);
   
   long facebookPhotosCount();

   boolean savePhotoImageToDisc(String realAlbumsPath, FacebookPhoto photo) throws IOException;

   Resource loadPhotoImageFromDisc(String realAlbumsPath, FacebookPhoto photo, boolean isThumbnail);
}
