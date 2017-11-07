package ma.hiddenfounders.codingchallenge.security.rest;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.hiddenfounders.codingchallenge.security.jwt.JwtUser;
import ma.hiddenfounders.codingchallenge.security.util.TokenUtil;

@RestController
public class UserRestController {

   @Value("${jwt.header}")
   private String tokenHeader;

   @Autowired
   private TokenUtil tokenUtil;

   @Autowired
   private UserDetailsService userDetailsService;

   @RequestMapping(value = "user", method = RequestMethod.GET)
   public ResponseEntity<JwtUser> getAuthenticatedUser(HttpServletRequest request) {
      // Exclude "Bearer " substring
      String token = request.getHeader(tokenHeader).substring(7);
      String email = tokenUtil.getEmailFromToken(token);
      JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(email);
      return ResponseEntity.ok(user);
   }
}
