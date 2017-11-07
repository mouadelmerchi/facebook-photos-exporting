package ma.hiddenfounders.codingchallenge.security.handler;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import ma.hiddenfounders.codingchallenge.security.util.SecurityUtils;

@Component
public class RestAccessDeniedHandler implements AccessDeniedHandler, Serializable {

   private static final long serialVersionUID = -686684529706066756L;

   /**
    * Returns a 403 error code (Forbidden) to the client.
    */
   @Override
   public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException exception)
         throws IOException, ServletException {
      SecurityUtils.sendError(response, exception, HttpServletResponse.SC_FORBIDDEN, "Not authorized resources");
   }
}