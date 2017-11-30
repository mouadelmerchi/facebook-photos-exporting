package ma.hiddenfounders.codingchallenge.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;
import ma.hiddenfounders.codingchallenge.repository.FacebookPhotoRepository;

@Service
class FacebookPhotoServiceImpl implements FacebookPhotoService {

   @Autowired
   private FacebookPhotoRepository fbPhotoRepository;

   @Override
   public FacebookPhoto saveFacebookPhoto(FacebookPhoto photo) throws IOException {
      if (savePhotoImageToDisc(photo)) {
         return fbPhotoRepository.save(photo);
      }
      return null;
   }

   private boolean savePhotoImageToDisc(FacebookPhoto photo) throws IOException {
      if (photo != null) {
         File file = new File(photo.getFilename());
         FileUtils.forceMkdirParent(file);
         FileCopyUtils.copy(photo.getPhotoBytes(), file);
         return true;
      }
      return false;
   }
}
