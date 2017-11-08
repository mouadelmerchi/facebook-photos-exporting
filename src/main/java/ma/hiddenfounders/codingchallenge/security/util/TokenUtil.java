package ma.hiddenfounders.codingchallenge.security.util;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mobile.device.Device;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetails;

@Component
public class TokenUtil implements Serializable {

   private static final long serialVersionUID = -5247527553686090633L;

   static final String AUDIENCE_UNKNOWN = "unknown";
   static final String AUDIENCE_WEB     = "web";
   static final String AUDIENCE_MOBILE  = "mobile";
   static final String AUDIENCE_TABLET  = "tablet";

   static final String CLAIM_KEY_USERNAME = "sub";
   static final String CLAIM_KEY_AUDIENCE = "aud";
   static final String CLAIM_KEY_CREATED  = "iat";

   @Autowired
   private TimeProvider timeProvider;

   @Value("${jwt.secret}")
   private String secret;

   @Value("${jwt.expiration}")
   private Long expiration;

   public String getEmailFromToken(String token) {
      return getClaimFromToken(token, Claims::getSubject);
   }

   public String getAudienceFromToken(String token) {
      return getClaimFromToken(token, Claims::getAudience);
   }

   public Instant getCreatedDateFromToken(String token) {
      return getClaimFromToken(token, Claims::getIssuedAt).toInstant();
   }

   public Instant getExpirationDateFromToken(String token) {
      return getClaimFromToken(token, Claims::getExpiration).toInstant();
   }

   private <T> T getClaimFromToken(String token, Function<Claims, T> claimsProducer) {
      final Claims claims = getClaimsFromToken(token);
      return claimsProducer.apply(claims);
   }

   private Claims getClaimsFromToken(String token) {
      Claims claims;
      try {
         claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
      } catch (Exception e) {
         claims = null;
      }
      return claims;
   }

   public String generateToken(UserDetails userDetails, Device device) {
      Map<String, Object> claims = new HashMap<>();
      String subject = userDetails.getUsername();
      String audience = generateAudience(device);

      return generateToken(claims, subject, audience);
   }

   public Boolean canTokenBeRefreshed(String token, Instant lastPasswordResetDate) {
      final Instant created = getCreatedDateFromToken(token);
      return !isCreatedBeforeLastPasswordReset(created, lastPasswordResetDate)
            && (!isTokenExpired(token) || ignoreTokenExpiration(token));
   }

   public String refreshToken(String token) {
      String refreshedToken;
      Instant createdDate = timeProvider.now();
      Instant expirationDate = generateExpirationDate();
      try {
         final Claims claims = getClaimsFromToken(token);
         claims.setIssuedAt(Date.from(createdDate));
         claims.setExpiration(Date.from(expirationDate));

         refreshedToken = Jwts.builder().setClaims(claims).signWith(SignatureAlgorithm.HS512, secret).compact();
      } catch (Exception e) {
         refreshedToken = null;
      }
      return refreshedToken;
   }

   private String generateToken(Map<String, Object> claims, String subject, String audience) {
      Instant createdDate = timeProvider.now();
      Instant expirationDate = generateExpirationDate();
      return Jwts.builder().setClaims(claims).setSubject(subject).setAudience(audience)
            .setIssuedAt(Date.from(createdDate)).setExpiration(Date.from(expirationDate))
            .signWith(SignatureAlgorithm.HS512, secret).compact();
   }

   private Instant generateExpirationDate() {
      return Instant.now().plusMillis(expiration * 1000);
   }

   public Boolean validateToken(String token, UserDetails userDetails) {
      JwtUserDetails user = (JwtUserDetails) userDetails;
      final String email = getEmailFromToken(token);
      final Instant created = getCreatedDateFromToken(token);
      return (email.equals(user.getUsername()) && !isTokenExpired(token)
            && !isCreatedBeforeLastPasswordReset(created, user.getLastPasswordResetDate()));
   }

   private Boolean isTokenExpired(String token) {
      final Instant expirationInstant = getExpirationDateFromToken(token);
      return expirationInstant.isBefore(timeProvider.now());
   }

   private Boolean isCreatedBeforeLastPasswordReset(Instant created, Instant lastPasswordResetDate) {
      return (lastPasswordResetDate != null && created.isBefore(lastPasswordResetDate));
   }

   private Boolean ignoreTokenExpiration(String token) {
      String audience = getAudienceFromToken(token);
      return (AUDIENCE_TABLET.equals(audience) || AUDIENCE_MOBILE.equals(audience));
   }

   private String generateAudience(Device device) {
      String audience = AUDIENCE_UNKNOWN;
      if (device.isNormal()) {
         audience = AUDIENCE_WEB;
      } else if (device.isTablet()) {
         audience = AUDIENCE_TABLET;
      } else if (device.isMobile()) {
         audience = AUDIENCE_MOBILE;
      }
      return audience;
   }
}
