package ma.hiddenfounders.codingchallenge.common;

public class Response extends Message {

   private int     status;
   private Message message;
   private Error   error;

   public Response(int status, Message message, Error error) {
      this.status = status;
      this.message = message;
      this.error = error;
   }

   public int getStatus() {
      return status;
   }

   public void setStatus(int status) {
      this.status = status;
   }

   public Message getMessage() {
      return message;
   }

   public void setMessage(Message message) {
      this.message = message;
   }

   public Error getError() {
      return error;
   }

   public void setError(Error error) {
      this.error = error;
   }
}