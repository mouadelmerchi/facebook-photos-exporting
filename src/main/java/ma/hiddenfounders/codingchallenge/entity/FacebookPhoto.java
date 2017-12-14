package ma.hiddenfounders.codingchallenge.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "facebookPhoto")
public class FacebookPhoto implements Serializable {

   private static final long serialVersionUID = 1548111687560080812L;

   private String id;

   private String name;

   private FacebookAlbumReference album;

   @Transient
   private byte[] photoBytes;

   private String imageKey;

   private String filename;

   private Date createdTime;

   private int width;

   private int height;

   public FacebookPhoto() {
   }

   public FacebookPhoto(String id, String name, FacebookAlbumReference album, byte[] photoBytes, Date createdTime,
         int width, int height, String imageKey, String fbImgExt) {
      this.id = id;
      this.name = name;
      this.album = album;
      this.photoBytes = photoBytes;
      this.createdTime = createdTime;
      this.width = width;
      this.height = height;
      this.imageKey = imageKey;

      String albumId = this.album.getId();
      String albumName = this.album.getName();
      albumName = ((StringUtils.isNotBlank(albumName) && StringUtils.isAlphanumericSpace(albumName)) ? albumName : albumId);
      this.filename = String.format("%s/a.%s.p.%s.%s", albumName, albumId, getId(), fbImgExt);
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

   public String getImageKey() {
      return imageKey;
   }

   public void setImageKey(String imageKey) {
      this.imageKey = imageKey;
   }

   public String getFilename() {
      return filename;
   }

   public void setFilename(String filename) {
      this.filename = filename;
   }

   public FacebookAlbumReference getAlbum() {
      return album;
   }

   public void setAlbum(FacebookAlbumReference album) {
      this.album = album;
   }

   public byte[] getPhotoBytes() {
      return photoBytes;
   }

   public void setPhotoBytes(byte[] photoBytes) {
      this.photoBytes = photoBytes;
   }

   public Date getCreatedTime() {
      return createdTime;
   }

   public void setCreatedTime(Date createdTime) {
      this.createdTime = createdTime;
   }

   public int getWidth() {
      return width;
   }

   public void setWidth(int width) {
      this.width = width;
   }

   public int getHeight() {
      return height;
   }

   public void setHeight(int height) {
      this.height = height;
   }

   @Override
   public String toString() {
      return "FacebookPhoto [id=" + id + ", name=" + name + ", album=" + album + ", createdTime=" + createdTime
            + ", width=" + width + ", height=" + height + "]";
   }
}
