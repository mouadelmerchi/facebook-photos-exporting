package ma.hiddenfounders.codingchallenge.common;

public class Error extends Message {

   private String reason;

   public Error(String reason, String message) {
      super(message);
      this.reason = reason;
   }

   public String getReason() {
      return reason;
   }

   public void setReason(String reason) {
      this.reason = reason;
   }
}