package ma.hiddenfounders.codingchallenge.entity;

import java.io.Serializable;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.facebook.api.PhotoReference;
import org.springframework.social.facebook.api.Album.Type;

@Document(collection = "facebookAlbum")
public class FacebookAlbum implements Serializable {

   private static final long serialVersionUID = -2686632635382032366L;

   @Id
   private String id;

   private String name;

   private int count;

   private PhotoReference coverPhoto;

   private Type type;

   public FacebookAlbum() {
   }

   public FacebookAlbum(String id, String name, int count, Type type) {
      this(id, name, count, null, type);
   }

   public FacebookAlbum(String id, String name, int count, PhotoReference coverPhoto, Type type) {
      this.id = id;
      this.name = name;
      this.count = count;
      this.coverPhoto = coverPhoto;
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

   public PhotoReference getCoverPhotoId() {
      return coverPhoto;
   }

   public void setCoverPhoto(PhotoReference coverPhoto) {
      this.coverPhoto = coverPhoto;
   }

   public Type getType() {
      return type;
   }

   public void setType(Type type) {
      this.type = type;
   }

   @Override
   public String toString() {
      return "FacebookAlbum [id=" + id + ", name=" + name + ", count=" + count + ", coverPhotoId=" + coverPhoto.getId()
            + ", type=" + type + "]";
   }
}
