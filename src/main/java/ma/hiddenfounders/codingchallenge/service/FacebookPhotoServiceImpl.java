package ma.hiddenfounders.codingchallenge.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;
import ma.hiddenfounders.codingchallenge.repository.FacebookPhotoRepository;

@Service
class FacebookPhotoServiceImpl implements FacebookPhotoService {

   @Autowired
   private FacebookPhotoRepository fbPhotoRepository;

   @Value("${app.facebook.images.extension}")
   private String facebookImagesExt;

   @Value("${app.facebook.images.thumbnailTargetWidth}")
   private Integer tumbnailTargetWidth;

   @Value("${app.facebook.images.thumbnailTargetHeight}")
   private Integer tumbnailTargetHeight;

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
   public Page<FacebookPhoto> getFacebookPhotos(FacebookPhoto probe, Pageable pageable) {
      return fbPhotoRepository.findAll(Example.of(probe), pageable);
   }

   @Override
   public boolean savePhotoImageToDisc(String realAlbumsPath, FacebookPhoto photo) throws IOException {
      if (photo != null) {
         Path parent = Paths.get(realAlbumsPath).resolve(photo.getAlbum().getOwner());

         File file = parent.resolve(photo.getFilename()).toFile();
         FileUtils.forceMkdirParent(file);
         FileCopyUtils.copy(photo.getPhotoBytes(), file);

         // Save thumbnail version of the image
         BufferedImage srcImage = ImageIO.read(file);
         BufferedImage scaledImage = Scalr.resize(srcImage, tumbnailTargetWidth, tumbnailTargetHeight);
         byte[] imageBytes = getBytesFromBufferedImage(scaledImage);

         File thumbnailFile = parent.resolve(photo.getThumbnailFilename()).toFile();

         FileCopyUtils.copy(imageBytes, thumbnailFile);

         return true;
      }
      return false;
   }

   @Override
   public Resource loadPhotoImageFromDisc(String realAlbumsPath, FacebookPhoto photo, boolean isThumbnail) {
      try {
         String filename = (isThumbnail ? photo.getThumbnailFilename() : photo.getFilename());
         URI uri = Paths.get(realAlbumsPath).resolve(photo.getAlbum().getOwner()).resolve(filename).toUri();
         Resource resource = new UrlResource(uri);
         if (resource.exists() || resource.isReadable()) {
            return resource;
         } else {
            throw new RuntimeException(String.format("Failed to load image '%s'!", filename));
         }
      } catch (MalformedURLException e) {
         throw new RuntimeException("FAIL!");
      }
   }

   private byte[] getBytesFromBufferedImage(BufferedImage image) throws IOException {
      try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
         ImageIO.write(image, facebookImagesExt, baos);
         baos.flush();
         return baos.toByteArray();
      } catch (IOException e) {
         throw e;
      }
   }
}
