package ma.hiddenfounders.codingchallenge.security.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import ma.hiddenfounders.codingchallenge.security.util.TokenUtil;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

   private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationTokenFilter.class);

   @Autowired
   private UserDetailsService userDetailsService;

   @Autowired
   private TokenUtil tokenUtil;

   @Value("${jwt.header}")
   private String tokenHeader;

   @Override
   protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
         throws ServletException, IOException {
      String authToken = request.getHeader(this.tokenHeader);

      if (authToken != null && authToken.startsWith("Bearer ")) {
         authToken = authToken.substring(7);
      }

      String email = tokenUtil.getEmailFromToken(authToken);

      LOGGER.info("Checking authentication for user {}", email);

      if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

         UserDetails userDetails = this.userDetailsService.loadUserByUsername(email);

         if (tokenUtil.validateToken(authToken, userDetails)) {
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                  null, userDetails.getAuthorities());
            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            LOGGER.info("Authenticated user '{}', setting security context", email);
            SecurityContextHolder.getContext().setAuthentication(authentication);
         }
      }

      chain.doFilter(request, response);
   }
}
