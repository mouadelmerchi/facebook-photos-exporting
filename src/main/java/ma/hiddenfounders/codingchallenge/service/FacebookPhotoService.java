package ma.hiddenfounders.codingchallenge.service;

import java.io.IOException;

import org.springframework.core.io.Resource;

import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;

public interface FacebookPhotoService {

   FacebookPhoto saveFacebookPhoto(FacebookPhoto photo);

   FacebookPhoto getFacebookPhotoById(String id);
   
   FacebookPhoto getFacebookPhotoByImageKey(String imageKey);
   
   boolean savePhotoImageToDisc(String realAlbumsPath, FacebookPhoto photo) throws IOException;
   
   Resource loadPhotoImageFromDisc(String realAlbumsPath, FacebookPhoto photo);
}
