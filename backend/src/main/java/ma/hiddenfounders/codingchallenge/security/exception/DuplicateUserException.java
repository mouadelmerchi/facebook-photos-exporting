package ma.hiddenfounders.codingchallenge.security.exception;

public class DuplicateUserException extends Exception {

   private static final long serialVersionUID = -8695126747312122059L;

   public DuplicateUserException(String msg, Throwable t) {
      super(msg, t);
   }

   public DuplicateUserException(String msg) {
      super(msg);
   }
}
