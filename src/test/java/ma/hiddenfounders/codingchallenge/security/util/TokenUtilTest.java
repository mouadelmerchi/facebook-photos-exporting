package ma.hiddenfounders.codingchallenge.security.util;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.sql.Date;
import java.time.Instant;

import org.assertj.core.util.DateUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.util.ReflectionTestUtils;

import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetails;

public class TokenUtilTest {

   private static final String TEST_EMAIL = "test@testprovider.com";

   @Mock
   private TimeProvider timeProviderMock;

   @InjectMocks
   private TokenUtil tokenUtil;

   @Before
   public void init() {
      MockitoAnnotations.initMocks(this);

      ReflectionTestUtils.setField(tokenUtil, "expiration", 3600L); // one hour
      ReflectionTestUtils.setField(tokenUtil, "secret", "SecretKey");
   }

   @Test
   public void testGenerateTokenGeneratesDifferentTokensForDifferentCreationDates() throws Exception {
      when(timeProviderMock.now()).thenReturn(DateUtil.yesterday().toInstant()).thenReturn(DateUtil.now().toInstant());

      final String token = createToken();
      final String laterToken = createToken();

      assertThat(token).isNotEqualTo(laterToken);
   }

   @Test
   public void testGetEmailFromToken() throws Exception {
      when(timeProviderMock.now()).thenReturn(DateUtil.now().toInstant());

      final String token = createToken();

      assertThat(tokenUtil.getEmailFromToken(token)).isEqualTo(TEST_EMAIL);
   }

   @Test
   public void testGetCreatedDateFromToken() throws Exception {
      final Instant now = DateUtil.now().toInstant();
      when(timeProviderMock.now()).thenReturn(now);

      final String token = createToken();

      assertThat(Date.from(tokenUtil.getCreatedDateFromToken(token))).isInSameMinuteWindowAs(Date.from(now));
   }

   @Test
   public void testGetExpirationDateFromToken() throws Exception {
      final Instant now = DateUtil.now().toInstant();
      when(timeProviderMock.now()).thenReturn(now);

      final String token = createToken();

      final Instant expirationDateFromToken = tokenUtil.getExpirationDateFromToken(token);
      assertThat(DateUtil.timeDifference(Date.from(expirationDateFromToken), Date.from(now))).isCloseTo(3600000L,
            within(1000L));
   }

   @Test
   public void testGetAudienceFromToken() throws Exception {
      when(timeProviderMock.now()).thenReturn(DateUtil.now().toInstant());

      final String token = createToken();

      assertThat(tokenUtil.getAudienceFromToken(token)).isEqualTo(TokenUtil.AUDIENCE_WEB);
   }

   public void testExpiredTokenCannotBeRefreshed() throws Exception {
      when(timeProviderMock.now()).thenReturn(DateUtil.yesterday().toInstant());

      String token = createToken();

     assertThat(tokenUtil.canTokenBeRefreshed(token, DateUtil.tomorrow().toInstant())).isFalse();
   }

   @Test
   public void testChangedPasswordCannotBeRefreshed() throws Exception {
      when(timeProviderMock.now()).thenReturn(DateUtil.now().toInstant());

      String token = createToken();

      assertThat(tokenUtil.canTokenBeRefreshed(token, DateUtil.tomorrow().toInstant())).isFalse();
   }

   @Test
   public void testNotExpiredCanBeRefreshed() {
      when(timeProviderMock.now()).thenReturn(DateUtil.now().toInstant());

      String token = createToken();

      assertThat(tokenUtil.canTokenBeRefreshed(token, DateUtil.yesterday().toInstant())).isTrue();
   }

   @Test
   public void testCanRefreshToken() throws Exception {
      when(timeProviderMock.now()).thenReturn(DateUtil.now().toInstant()).thenReturn(DateUtil.tomorrow().toInstant());

      String firstToken = createToken();
      String refreshedToken = tokenUtil.refreshToken(firstToken);

      Instant firstTokenDate = tokenUtil.getCreatedDateFromToken(firstToken);
      Instant refreshedTokenDate = tokenUtil.getCreatedDateFromToken(refreshedToken);
      assertThat(Date.from(firstTokenDate)).isBefore(Date.from(refreshedTokenDate));
   }

   @Test
   public void testCanValidateToken() throws Exception {
      when(timeProviderMock.now()).thenReturn(DateUtil.now().toInstant());

      UserDetails userDetails = mock(JwtUserDetails.class);

      when(userDetails.getUsername()).thenReturn(TEST_EMAIL);

      String token = createToken();

      assertThat(tokenUtil.validateToken(token, userDetails)).isTrue();
   }

   private String createToken() {
      final DeviceMock device = new DeviceMock();

      device.setNormal(true);

      return tokenUtil.generateToken(new UserDetailsMock(TEST_EMAIL), device);
   }
}
