package ma.hiddenfounders.codingchallenge.dto;

import java.util.List;

public class PhotosListResponse extends PagedListResponse {

   private static final long serialVersionUID = -3472928714280870706L;

   private List<FacebookPhotoDTO> photos;
   private String                 albumName;

   public PhotosListResponse() {
      super();
   }

   public PhotosListResponse(List<FacebookPhotoDTO> photos, String albumName, long totalCount, int pageSize,
         int pagesToShow) {
      super(totalCount, pageSize, pagesToShow);
      this.photos = photos;
      this.albumName = albumName;
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

   @Override
   public String toString() {
      return "PhotosListResponse [photos=" + photos + ", albumName=" + albumName + ", PagedListResponse="
            + super.toString() + "]";
   }
}
