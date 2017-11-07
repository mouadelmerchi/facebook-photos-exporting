package ma.hiddenfounders.codingchallenge.security.jwt;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import ma.hiddenfounders.codingchallenge.security.entity.Authority;
import ma.hiddenfounders.codingchallenge.security.entity.User;

public class JwtUserFactory {

   private JwtUserFactory() {
   }

   public static JwtUser create(User user) {
       return new JwtUser(
               user.getId(),
               user.getUsername(),
               user.getEmail(),
               user.getPassword(),
               user.getEnabled(),
               user.getLastPasswordResetDate(),
               mapToGrantedAuthorities(user.getAuthorities())
       );
   }

   private static List<GrantedAuthority> mapToGrantedAuthorities(List<Authority> authorities) {
      return authorities
            .stream()
            .map(authority -> new SimpleGrantedAuthority(authority.getName().name()))
            .collect(Collectors.toList());
   }
}
