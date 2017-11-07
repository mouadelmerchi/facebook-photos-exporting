package ma.hiddenfounders.codingchallenge.security.util;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {

   private static final long serialVersionUID = -4246677518475722484L;

   private String email;
   private String password;

   public AuthenticationRequest() {
   }

   public AuthenticationRequest(String email, String password) {
      setEmail(email);
      setPassword(password);
   }

   public String getEmail() {
      return this.email;
   }

   public void setEmail(String email) {
      this.email = email;
   }

   public String getPassword() {
      return this.password;
   }

   public void setPassword(String password) {
      this.password = password;
   }

   @Override
   public int hashCode() {
      final int prime = 31;
      int result = 1;
      result = prime * result + ((email == null) ? 0 : email.hashCode());
      result = prime * result + ((password == null) ? 0 : password.hashCode());
      return result;
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj)
         return true;
      if (obj == null)
         return false;
      if (getClass() != obj.getClass())
         return false;
      AuthenticationRequest other = (AuthenticationRequest) obj;
      if (email == null) {
         if (other.email != null)
            return false;
      } else if (!email.equals(other.email))
         return false;
      if (password == null) {
         if (other.password != null)
            return false;
      } else if (!password.equals(other.password))
         return false;
      return true;
   }

   @Override
   public String toString() {
      return "AuthenticationRequest [email=" + email + ", password=" + password + "]";
   }
}
