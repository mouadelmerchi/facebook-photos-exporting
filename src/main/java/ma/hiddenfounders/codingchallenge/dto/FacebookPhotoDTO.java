package ma.hiddenfounders.codingchallenge.dto;

import java.io.Serializable;

public class FacebookPhotoDTO implements Serializable {

   private static final long serialVersionUID = 1217760305285461653L;

   private String id;

   private String name;

   private String photoUri;

   private String imageKey;

   public FacebookPhotoDTO() {
   }

   public FacebookPhotoDTO(String id, String name, String photoUri, String imageKey) {
      this.id = id;
      this.name = name;
      this.photoUri = photoUri;
      this.imageKey = imageKey;
   }

   public String getId() {
      return id;
   }

   public void setId(String id) {
      this.id = id;
   }

   public String getName() {
      return name;
   }

   public void setName(String name) {
      this.name = name;
   }

   public String getPhotoUri() {
      return photoUri;
   }

   public void setPhotoUri(String photoUri) {
      this.photoUri = photoUri;
   }

   public String getImageKey() {
      return imageKey;
   }

   public void setImageKey(String imageKey) {
      this.imageKey = imageKey;
   }

   @Override
   public String toString() {
      return "FacebookPhotoDTO [id=" + id + ", name=" + name + ", photoUri=" + photoUri
            + ", imageKey=" + imageKey + "]";
   }
}
