package ma.hiddenfounders.codingchallenge.entity;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.social.facebook.api.Reference;

@Document(collection = "facebookPhoto")
public class FacebookPhoto implements Serializable {

   private static final long serialVersionUID = 1548111687560080812L;

   private String id;

   private String name;

   private Reference album;

   @Transient
   private byte[] photoBytes;

   @Transient
   private String filename;

   private Date createdTime;

   private int width;

   private int height;

   public FacebookPhoto(String id, String name, Reference album, byte[] photoBytes, Date createdTime, int width,
         int height, String fbImgPath, String fbImgExt) {
      this.id = id;
      this.name = name;
      this.album = album;
      this.photoBytes = photoBytes;
      this.createdTime = createdTime;
      this.width = width;
      this.height = height;
      
      String albumId = this.album.getId();
      String albumName = this.album.getName();
      String albumTitle = ((StringUtils.isNotBlank(albumName) && StringUtils.isAlphanumericSpace(albumName)) ? albumName : albumId);
      this.filename = String.format("%s/%s/a.%s.p.%s.%s", fbImgPath, albumTitle, albumId, getId(), fbImgExt);
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

   public String getFilename() {
      return filename;
   }

   public Reference getAlbum() {
      return album;
   }

   public void setAlbum(Reference album) {
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
