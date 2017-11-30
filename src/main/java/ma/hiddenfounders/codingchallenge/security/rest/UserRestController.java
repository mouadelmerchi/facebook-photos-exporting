package ma.hiddenfounders.codingchallenge.security.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.hiddenfounders.codingchallenge.security.dto.AuthenticationResponse;
import ma.hiddenfounders.codingchallenge.security.entity.User;
import ma.hiddenfounders.codingchallenge.security.exception.DuplicateUserException;
import ma.hiddenfounders.codingchallenge.security.exception.InvalidUserInfoException;
import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetails;
import ma.hiddenfounders.codingchallenge.security.service.UserService;
import ma.hiddenfounders.codingchallenge.security.util.AuthenticationUtil;
import ma.hiddenfounders.codingchallenge.security.util.SecurityUtils;
import ma.hiddenfounders.codingchallenge.security.util.TokenUtil;

@RestController
public class UserRestController {

   @Value("${jwt.header}")
   private String tokenHeader;

   @Value("/${jwt.route.authentication.path}")
   private String authPath;

   @Autowired
   private AuthenticationUtil authUtil;

   @Autowired
   private TokenUtil tokenUtil;

   @Autowired
   private UserDetailsService userDetailsService;

   @Autowired
   private UserService userService;

   @RequestMapping(value = "/auth/user", method = RequestMethod.GET)
   public void getAuthenticatedUser(HttpServletRequest request, HttpServletResponse response) throws IOException {
      String authToken = request.getHeader(tokenHeader);

      // Exclude "Bearer " substring if it exists
      if (authToken != null && authToken.startsWith("Bearer ")) {
         authToken = authToken.substring(7);
      }

      String email = tokenUtil.getEmailFromToken(authToken);
      JwtUserDetails user = (JwtUserDetails) userDetailsService.loadUserByUsername(email);
      SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, user);
   }

   @RequestMapping(value = "/auth/user", method = RequestMethod.POST)
   public void saveAndAuthenticateUser(@RequestBody User user, HttpServletResponse response, Device device)
         throws AuthenticationException, IOException {
      try {
         String unhashedPassword = user.getPassword();
         userService.saveNewUser(user);

         String token = authUtil.authenticateAndGenerateToken(user.getEmail(), unhashedPassword, device);
         SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, new AuthenticationResponse(token));

      } catch (DuplicateUserException dupException) {
         String message = String.format("Email '%s' and/or username '%s' are already taken", user.getEmail(),
               user.getUsername());
         SecurityUtils.sendError(response, dupException, HttpServletResponse.SC_BAD_REQUEST, message);

      } catch (InvalidUserInfoException invalidInfoException) {
         SecurityUtils.sendError(response, invalidInfoException, HttpServletResponse.SC_BAD_REQUEST, "Please provide valid values before submitting");
         
      } catch (Exception e) {
         SecurityUtils.sendError(response,
               new Exception("System error occurred"),
               HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "The system is unavailable at this time. Please try again later");
      }
   }
}
