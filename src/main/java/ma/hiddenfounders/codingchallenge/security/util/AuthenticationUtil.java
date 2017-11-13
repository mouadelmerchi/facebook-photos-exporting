package ma.hiddenfounders.codingchallenge.security.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class AuthenticationUtil {

   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   private TokenUtil tokenUtil;

   @Autowired
   private UserDetailsService userDetailsService;

   public String authenticateAndGenerateToken(String email, String password, Device device) {
      // Perform the authentication
      final Authentication authentication = authenticationManager
            .authenticate(new UsernamePasswordAuthenticationToken(email, password));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // Generate a token
      final UserDetails userDetails = userDetailsService.loadUserByUsername(email);
      final String token = tokenUtil.generateToken(userDetails, device);

      return token;
   }
}
