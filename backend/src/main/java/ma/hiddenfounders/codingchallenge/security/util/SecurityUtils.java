package ma.hiddenfounders.codingchallenge.security.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.ObjectMapper;

import ma.hiddenfounders.codingchallenge.common.Error;
import ma.hiddenfounders.codingchallenge.common.Message;
import ma.hiddenfounders.codingchallenge.common.Response;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

   private static final Logger LOGGER = LoggerFactory.getLogger(SecurityUtils.class);
   
   private static final ObjectMapper MAPPER = new ObjectMapper();

   private SecurityUtils() {
   }

   public static String getCurrentUserEmail() {
      SecurityContext securityContext = SecurityContextHolder.getContext();
      Authentication authentication = securityContext.getAuthentication();
      UserDetails springSecurityUser = null;
      String email = null;

      if (authentication != null) {
         LOGGER.info("Authentication principal: {}", authentication.getPrincipal());
         if (authentication.getPrincipal() instanceof UserDetails) {
            springSecurityUser = (UserDetails) authentication.getPrincipal();
            email = springSecurityUser.getUsername();
         } else if (authentication.getPrincipal() instanceof String) {
            email = (String) authentication.getPrincipal();
         }
         System.out.println("####### " + email + " ########");
      }

      return email;
   }

   public static void sendError(HttpServletResponse response, Exception exception, int status, String message)
         throws IOException {
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(status);
      PrintWriter writer = response.getWriter();
      Error error = new Error("Authentication error", exception.getMessage());
      Message msg = new Message(message);
      writer.write(MAPPER.writeValueAsString(new Response(status, msg, error)));
      writer.flush();
      writer.close();
   }

   public static void sendResponse(HttpServletResponse response, int status, Object object) throws IOException {
      response.setContentType("application/json;charset=UTF-8");
      response.setStatus(status);
      PrintWriter writer = response.getWriter();
      writer.write(MAPPER.writeValueAsString(object));
      writer.flush();
      writer.close();
   }
}