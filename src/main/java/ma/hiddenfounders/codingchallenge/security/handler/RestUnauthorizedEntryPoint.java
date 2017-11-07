package ma.hiddenfounders.codingchallenge.security.handler;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import ma.hiddenfounders.codingchallenge.security.util.SecurityUtils;

@Component
public class RestUnauthorizedEntryPoint implements AuthenticationEntryPoint, Serializable {

   private static final long serialVersionUID = 8450539805106739229L;

   /**
    * Returns a 401 error code (Unauthorized) to the client.
    */
   @Override
   public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
         throws IOException, ServletException {
      SecurityUtils.sendError(response, exception, HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
   }
}