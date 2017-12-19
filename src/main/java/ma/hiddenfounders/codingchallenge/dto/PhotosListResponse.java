package ma.hiddenfounders.codingchallenge.dto;

import java.io.Serializable;
import java.util.List;

public class PhotosListResponse implements Serializable {

   private static final long serialVersionUID = -3472928714280870706L;

   private List<FacebookPhotoDTO> photos;
   private String                 albumName;
   private int                    currentPage;
   private int                    pageSize;

   public PhotosListResponse() {
   }

   public PhotosListResponse(List<FacebookPhotoDTO> photos, String albumName, int currentPage, int pageSize) {
      this.photos = photos;
      this.albumName = albumName;
      this.currentPage = currentPage;
      this.pageSize = pageSize;
   }

   public List<FacebookPhotoDTO> getPhotos() {
      return photos;
   }

   public void setPhotos(List<FacebookPhotoDTO> photos) {
      this.photos = photos;
   }

   public String getAlbumName() {
      return albumName;
   }

   public void setAlbumName(String albumName) {
      this.albumName = albumName;
   }

   public int getCurrentPage() {
      return currentPage;
   }

   public void setCurrentPage(int currentPage) {
      this.currentPage = currentPage;
   }

   public int getPageSize() {
      return pageSize;
   }

   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }

   @Override
   public String toString() {
      return "PhotosListResponse [photos=" + photos + ", currentPage=" + currentPage + ", pageSize=" + pageSize + "]";
   }
}
