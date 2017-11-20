package ma.hiddenfounders.codingchallenge.dto;

import java.io.Serializable;
import java.util.List;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;

public class AlbumsListResponse implements Serializable {

   private static final long serialVersionUID = -1440968978853249202L;

   private List<FacebookAlbum> albums;
   private int                 currentPage;
   private int                 pageSize;

   public AlbumsListResponse() {
   }

   public AlbumsListResponse(List<FacebookAlbum> albums, int currentPage, int pageSize) {
      this.albums = albums;
      this.currentPage = currentPage;
      this.pageSize = pageSize;
   }

   public List<FacebookAlbum> getAlbums() {
      return albums;
   }

   public void setAlbums(List<FacebookAlbum> albums) {
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
