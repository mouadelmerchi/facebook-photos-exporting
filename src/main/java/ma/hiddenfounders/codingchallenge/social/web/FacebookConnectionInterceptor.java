package ma.hiddenfounders.codingchallenge.social.web;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.HttpSessionSessionStrategy;
import org.springframework.social.connect.web.SessionStrategy;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

import ma.hiddenfounders.codingchallenge.service.FacebookImportService;

@Component
public class FacebookConnectionInterceptor implements ConnectInterceptor<Facebook> {

   private static final Logger LOGGER = LoggerFactory.getLogger(FacebookConnectionInterceptor.class);

   private static final String USER_EMAIL_ATTRIBUTE = "userEmail";

   @Autowired
   FacebookImportService facebookConnectionService;

   @Autowired
   private ServletContext context;

   @Value("${app.facebook.images.path}")
   private String facebookAlbumsPath;

   @Value("${app.facebook.defaultPageSize}")
   private Integer defaultPageSize;

   @Value("${app.facebook.images.extension}")
   private String facebookImagesExt;

   private SessionStrategy sessionStrategy;

   @PostConstruct
   public void setup() {
      sessionStrategy = new HttpSessionSessionStrategy();
   }

   @Override
   public void preConnect(ConnectionFactory<Facebook> connectionFactory, MultiValueMap<String, String> parameters, WebRequest request) {
      String requestUserEmail = request.getParameter("userEmail");
      LOGGER.info("########### preConnect in Interceptor: User email {} #############", requestUserEmail);
      sessionStrategy.setAttribute(request, USER_EMAIL_ATTRIBUTE, requestUserEmail);
   }

   @Override
   public void postConnect(Connection<Facebook> connection, WebRequest request) {
      String requestUserEmail = extractCachedRequestUserEmail(request);
      LOGGER.info("########### postConnect In Interceptor: User email {} #############", requestUserEmail);
      String realAlbumsPath = context.getRealPath(facebookAlbumsPath);
      facebookConnectionService.importFacebookAlbums(requestUserEmail, realAlbumsPath, defaultPageSize,
            facebookImagesExt);
   }

   private String extractCachedRequestUserEmail(WebRequest request) {
      String requestUserEmail = (String) sessionStrategy.getAttribute(request, USER_EMAIL_ATTRIBUTE);
      sessionStrategy.removeAttribute(request, USER_EMAIL_ATTRIBUTE);
      return requestUserEmail;
   }
}