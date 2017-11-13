package ma.hiddenfounders.codingchallenge.security.exception;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

   private static final long serialVersionUID = 524149919985041301L;

   public InvalidTokenException(String msg, Throwable t) {
      super(msg, t);
   }

   public InvalidTokenException(String msg) {
      super(msg);
   }
}