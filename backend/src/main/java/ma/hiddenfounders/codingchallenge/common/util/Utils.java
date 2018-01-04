package ma.hiddenfounders.codingchallenge.common.util;

import java.util.UUID;

import org.springframework.social.facebook.api.Facebook;

public final class Utils {

   public static final String FACEBOOK_SCOPE = "user_photos";

   private Utils() {
   }

   public static String generateImageKey() {
      return String.format("%s-%s-%s", UUID.randomUUID(), UUID.randomUUID(), UUID.randomUUID());
   }

   public static boolean isAuthorized(Facebook facebook) {
      try {
         return facebook.isAuthorized();
      } catch (Throwable t) {
         return false;
      }
   }
}
