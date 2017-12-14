package ma.hiddenfounders.codingchallenge.security.jwt;

import java.time.Instant;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class JwtUserDetails implements UserDetails {

   private static final long serialVersionUID = -3053132814966759634L;

   private final Long                                   id;
   private final String                                 username;
   private final String                                 email;
   private final String                                 password;
   private final boolean                                enabled;
   private final Instant                                lastPasswordResetDate;
   private final Collection<? extends GrantedAuthority> authorities;

   public JwtUserDetails(Long id, String username, String email, String password, boolean enabled,
         Instant lastPasswordResetDate, Collection<? extends GrantedAuthority> authorities) {
      this.id = id;
      this.username = username;
      this.email = email;
      this.password = password;
      this.enabled = enabled;
      this.lastPasswordResetDate = lastPasswordResetDate;
      this.authorities = authorities;
   }

   @JsonIgnore
   public Long getId() {
      return id;
   }

   public String getEmail() {
      return email;
   }

   @JsonIgnore
   @Override
   public String getPassword() {
      return password;
   }

   @Override
   public boolean isEnabled() {
      return enabled;
   }

   @JsonIgnore
   public Instant getLastPasswordResetDate() {
      return lastPasswordResetDate;
   }

   @Override
   public String getUsername() {
      return email;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @JsonIgnore
   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return authorities;
   }

   @Override
   public String toString() {
      return "JwtUserDetails [id=" + id + ", username=" + username + ", email=" + email + "]";
   }
}
