package ma.hiddenfounders.codingchallenge.dto;

import java.io.Serializable;

public class PagedListResponse implements Serializable {

   private static final long serialVersionUID = -3755052954113803624L;

   private long totalCount;
   private int pageSize;
   private int pagesToShow;

   public PagedListResponse() {
   }

   public PagedListResponse(long totalCount, int pageSize, int pagesToShow) {
      this.totalCount = totalCount;
      this.pageSize = pageSize;
      this.pagesToShow = pagesToShow;
   }

   public long getTotalCount() {
      return totalCount;
   }

   public void setTotalCount(long totalCount) {
      this.totalCount = totalCount;
   }

   public int getPageSize() {
      return pageSize;
   }

   public void setPageSize(int pageSize) {
      this.pageSize = pageSize;
   }

   public int getPagesToShow() {
      return pagesToShow;
   }

   public void setPagesToShow(int pagesToShow) {
      this.pagesToShow = pagesToShow;
   }

   @Override
   public String toString() {
      return "ListResponse [totalCount=" + totalCount + ", pageSize=" + pageSize + ", pagesToShow=" + pagesToShow + "]";
   }
}
