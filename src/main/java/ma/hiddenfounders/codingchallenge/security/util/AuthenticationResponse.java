package ma.hiddenfounders.codingchallenge.security.util;

import java.io.Serializable;

public class AuthenticationResponse implements Serializable {

   private static final long serialVersionUID = 2914390259256012758L;

   private final int    hash;
   private final String token;

   public AuthenticationResponse(String token) {
      this.hash = 31 + ((token == null) ? 0 : token.hashCode());
      this.token = token;
   }

   public String getToken() {
      return this.token;
   }

   @Override
   public int hashCode() {
      return hash;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      AuthenticationResponse other = (AuthenticationResponse) obj;
      if (token == null) {
         if (other.token != null)
            return false;
      } else if (!token.equals(other.token))
         return false;
      return true;
   }
}
