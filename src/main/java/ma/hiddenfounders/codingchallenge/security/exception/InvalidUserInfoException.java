package ma.hiddenfounders.codingchallenge.security.exception;

public class InvalidUserInfoException extends Exception {

   private static final long serialVersionUID = 6850962551177185444L;

   public InvalidUserInfoException(String msg, Throwable t) {
      super(msg, t);
   }

   public InvalidUserInfoException(String msg) {
      super(msg);
   }
}
