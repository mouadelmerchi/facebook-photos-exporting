package ma.hiddenfounders.codingchallenge.dto;

import java.io.Serializable;

import org.springframework.social.facebook.api.Album.Type;

public class FacebookAlbumDTO implements Serializable {

   private static final long serialVersionUID = -9002813190722744238L;

   private String id;

   private String name;

   private int count;

   private String coverPhotoUri;

   private Type type;

   public FacebookAlbumDTO() {
   }

   public FacebookAlbumDTO(String id, String name, int count, String coverPhotoUri, Type type) {
      this.id = id;
      this.name = name;
      this.count = count;
      this.coverPhotoUri = coverPhotoUri;
      this.type = type;
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

   public int getCount() {
      return count;
   }

   public void setCount(int count) {
      this.count = count;
   }

   public String getCoverPhotoUri() {
      return coverPhotoUri;
   }

   public void setCoverPhotoUri(String coverPhotoUri) {
      this.coverPhotoUri = coverPhotoUri;
   }

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }

   @Override
   public String toString() {
      return "FacebookAlbumDTO [id=" + id + ", name=" + name + ", count=" + count + ", coverPhotoUri="
            + coverPhotoUri + ", type=" + type + "]";
   }
}
