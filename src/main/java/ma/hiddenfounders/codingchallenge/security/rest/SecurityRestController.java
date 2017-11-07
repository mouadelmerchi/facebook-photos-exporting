package ma.hiddenfounders.codingchallenge.security.rest;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.hiddenfounders.codingchallenge.security.jwt.JwtUser;
import ma.hiddenfounders.codingchallenge.security.util.AuthenticationRequest;
import ma.hiddenfounders.codingchallenge.security.util.AuthenticationResponse;
import ma.hiddenfounders.codingchallenge.security.util.SecurityUtils;
import ma.hiddenfounders.codingchallenge.security.util.TokenUtil;

@RestController
@RequestMapping
public class SecurityRestController {

   @Value("${jwt.header}")
   private String tokenHeader;

   @Autowired
   private AuthenticationManager authenticationManager;

   @Autowired
   private TokenUtil tokenUtil;

   @Autowired
   private UserDetailsService userDetailsService;

   @RequestMapping(value = "${jwt.route.authentication.path}", method = RequestMethod.POST)
   public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,
         HttpServletResponse response, Device device) throws AuthenticationException, IOException {

      // Perform the authentication
      final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            authenticationRequest.getEmail(), authenticationRequest.getPassword()));
      SecurityContextHolder.getContext().setAuthentication(authentication);

      // Generate a token
      final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());
      final String token = tokenUtil.generateToken(userDetails, device);

      // Return the token
      SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, new AuthenticationResponse(token));
   }

   @RequestMapping(value = "${jwt.route.authentication.refresh}", method = RequestMethod.GET)
   public void refreshAndGetAuthenticationToken(HttpServletRequest request, HttpServletResponse response)
         throws IOException {
      String token = request.getHeader(tokenHeader);
      String email = tokenUtil.getEmailFromToken(token);
      JwtUser user = (JwtUser) userDetailsService.loadUserByUsername(email);

      if (tokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
         String refreshedToken = tokenUtil.refreshToken(token);
         SecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, new AuthenticationResponse(refreshedToken));
      } else {
         SecurityUtils.sendResponse(response, HttpServletResponse.SC_BAD_REQUEST, null);
      }
   }
}