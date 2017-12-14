package ma.hiddenfounders.codingchallenge.dto;

import java.io.Serializable;
import java.util.List;

public class AlbumsListResponse implements Serializable {

   private static final long serialVersionUID = -1440968978853249202L;

   private List<FacebookAlbumDTO> albums;
   private int                    currentPage;
   private int                    pageSize;

   public AlbumsListResponse() {
   }

   public AlbumsListResponse(List<FacebookAlbumDTO> albums, int currentPage, int pageSize) {
      this.albums = albums;
      this.currentPage = currentPage;
      this.pageSize = pageSize;
   }

   public List<FacebookAlbumDTO> getAlbums() {
      return albums;
   }

   public void setAlbums(List<FacebookAlbumDTO> albums) {
      this.albums = albums;
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
}
