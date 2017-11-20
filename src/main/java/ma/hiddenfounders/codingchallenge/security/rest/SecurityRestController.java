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

import ma.hiddenfounders.codingchallenge.security.dto.AuthenticationRequest;
import ma.hiddenfounders.codingchallenge.security.dto.AuthenticationResponse;
import ma.hiddenfounders.codingchallenge.security.exception.InvalidTokenException;
import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetails;
import ma.hiddenfounders.codingchallenge.security.util.AuthenticationUtil;
import ma.hiddenfounders.codingchallenge.security.util.SecurityUtils;
import ma.hiddenfounders.codingchallenge.security.util.TokenUtil;

@RestController
@RequestMapping
public class SecurityRestController {

   @Value("${jwt.header}")
   private String tokenHeader;

   @Autowired
   private AuthenticationUtil authUtil;

   @Autowired
   private TokenUtil tokenUtil;

   @Autowired
   private UserDetailsService userDetailsService;

   @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
   public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
         HttpServletResponse response, Device device) throws AuthenticationException, IOException {

      String token = authUtil.authenticateAndGenerateToken(authenticationRequest.getEmail(),
            authenticationRequest.getPassword(), device);

      SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, new AuthenticationResponse(token));
   }

   @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
   public void refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response)
         throws IOException {
      String token = request.getHeader(tokenHeader);
      String email = tokenUtil.getEmailFromToken(token);
      JwtUserDetails user = (JwtUserDetails) userDetailsService.loadUserByUsername(email);

      if (tokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
         String refreshedToken = tokenUtil.refreshToken(token);
         SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, new AuthenticationResponse(refreshedToken));
      } else {
         SecurityUtils.sendError(response, new InvalidTokenException("Invalid token value"),
               HttpServletResponse.SC_BAD_REQUEST, "Please sign in to be able to access the requested resource.");
      }
   }
}