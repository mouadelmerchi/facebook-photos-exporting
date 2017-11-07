package ma.hiddenfounders.codingchallenge.common;

public class Response extends Message {

   private int     code;
   private Message message;
   private Error   error;

   public Response(int code, Message message, Error error) {
      this.code = code;
      this.message = message;
      this.error = error;
   }

   public int getCode() {
      return code;
   }

   public void setCode(int code) {
      this.code = code;
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