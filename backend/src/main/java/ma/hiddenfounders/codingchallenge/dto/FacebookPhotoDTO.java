package ma.hiddenfounders.codingchallenge.dto;

import java.io.Serializable;

public class FacebookPhotoDTO implements Serializable {

   private static final long serialVersionUID = 1217760305285461653L;

   private String id;

   private String name;

   private String photoUri;

   private String photoThumbUri;

   public FacebookPhotoDTO() {
   }

   public FacebookPhotoDTO(String id, String name, String photoUri, String photoThumbUri) {
      this.id = id;
      this.name = name;
      this.photoUri = photoUri;
      this.photoThumbUri = photoThumbUri;
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

   public String getPhotoThumbUri() {
      return photoThumbUri;
   }

   public void setPhotoThumbUri(String photoThumbUri) {
      this.photoThumbUri = photoThumbUri;
   }

   @Override
   public String toString() {
      return "FacebookPhotoDTO [id=" + id + ", name=" + name + ", photoUri=" + photoUri + ", photoThumbUri=" + photoThumbUri + "]";
   }
}
