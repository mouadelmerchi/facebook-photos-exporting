package ma.hiddenfounders.codingchallenge.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Paths;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;
import ma.hiddenfounders.codingchallenge.repository.FacebookPhotoRepository;

@Service
class FacebookPhotoServiceImpl implements FacebookPhotoService {

   @Autowired
   private FacebookPhotoRepository fbPhotoRepository;

   @Override
   public FacebookPhoto saveFacebookPhoto(FacebookPhoto photo) {
      return fbPhotoRepository.save(photo);
   }

   @Override
   public FacebookPhoto getFacebookPhotoById(String id) {
      Optional<FacebookPhoto> photoOpt = fbPhotoRepository.findById(id);
      return photoOpt.orElse(null);
   }

   @Override
   public FacebookPhoto getFacebookPhotoByImageKey(String imageKey) {
      return fbPhotoRepository.findByImageKey(imageKey);
   }

   @Override
   public boolean savePhotoImageToDisc(String realAlbumsPath, FacebookPhoto photo) throws IOException {
      if (photo != null) {
         File file = Paths.get(realAlbumsPath)
               .resolve(photo.getAlbum().getOwner())
               .resolve(photo.getFilename())
               .toFile();
         FileUtils.forceMkdirParent(file);
         FileCopyUtils.copy(photo.getPhotoBytes(), file);
         return true;
      }
      return false;
   }

   @Override
   public Resource loadPhotoImageFromDisc(String realAlbumsPath, FacebookPhoto photo) {
      try {
         URI uri = Paths.get(realAlbumsPath)
               .resolve(photo.getAlbum().getOwner())
               .resolve(photo.getFilename())
               .toUri();
         Resource resource = new UrlResource(uri);
         if (resource.exists() || resource.isReadable()) {
            return resource;
         } else {
            throw new RuntimeException(String.format("Failed to load image '%s'!", photo.getFilename()));
         }
      } catch (MalformedURLException e) {
         throw new RuntimeException("FAIL!");
      }
   }
}
