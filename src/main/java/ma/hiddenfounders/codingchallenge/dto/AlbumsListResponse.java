package ma.hiddenfounders.codingchallenge.dto;

import java.util.List;

public class AlbumsListResponse extends PagedListResponse {

   private static final long serialVersionUID = -1440968978853249202L;

   private List<FacebookAlbumDTO> albums;

   public AlbumsListResponse() {
   }

   public AlbumsListResponse(List<FacebookAlbumDTO> albums, long totalCount, int pageSize, int pagesToShow) {
      super(totalCount, pageSize, pagesToShow);
      this.albums = albums;
   }

   public List<FacebookAlbumDTO> getAlbums() {
      return albums;
   }

   public void setAlbums(List<FacebookAlbumDTO> albums) {
      this.albums = albums;
   }

   @Override
   public String toString() {
      return "AlbumsListResponse [albums=" + albums + ", PagedListResponse=" + super.toString() + "]";
   }
}
