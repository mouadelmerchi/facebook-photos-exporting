package ma.hiddenfounders.codingchallenge.security.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import ma.hiddenfounders.codingchallenge.security.dto.AuthenticationRequest;
import ma.hiddenfounders.codingchallenge.security.entity.Authority;
import ma.hiddenfounders.codingchallenge.security.entity.AuthorityName;
import ma.hiddenfounders.codingchallenge.security.entity.User;
import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetails;
import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetailsFactory;
import ma.hiddenfounders.codingchallenge.security.util.TokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SecurityRestControllerTest {

   private static final ObjectMapper MAPPER = new ObjectMapper();

   private MockMvc mvc;

   @Value("/${jwt.route.authentication.path}")
   private String authPath;

   @Value("/${jwt.route.authentication.refresh}")
   private String authRefresh;

   @Autowired
   private WebApplicationContext context;

   @MockBean
   private AuthenticationManager authenticationManager;

   @MockBean
   private TokenUtil tokenUtil;

   @MockBean
   private UserDetailsService userDetailsService;

   @Before
   public void setup() {
      mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
   }

   @Test
   @WithAnonymousUser
   public void testSuccessfulAuthenticationWithAnonymousUser() throws Exception {
      System.out.println(authPath);
      AuthenticationRequest authenticationRequest = new AuthenticationRequest("enabled@user.com", "password");
      this.mvc.perform(post(authPath).contentType(MediaType.APPLICATION_JSON)
            .content(MAPPER.writeValueAsString(authenticationRequest))).andExpect(status().is2xxSuccessful());
   }

   @Test
   @WithMockUser(roles = "USER")
   public void testSuccessfulRefreshTokenWithUserRole() throws Exception {
      Authority authority = new Authority();
      authority.setId(0L);
      authority.setName(AuthorityName.ROLE_USER);
      List<Authority> authorities = Arrays.asList(authority);

      User user = new User();
      user.setEmail("enabled@user.com");
      user.setAuthorities(authorities);
      user.setEnabled(Boolean.TRUE);
      user.setLastPasswordResetDate(Instant.now().plusMillis(1000 * 1000));

      JwtUserDetails jwtUser = JwtUserDetailsFactory.create(user);

      when(tokenUtil.getEmailFromToken(any())).thenReturn(user.getEmail());

      when(userDetailsService.loadUserByUsername(eq(user.getEmail()))).thenReturn(jwtUser);

      when(tokenUtil.canTokenBeRefreshed(any(), any())).thenReturn(true);

      this.mvc.perform(get(authRefresh)).andExpect(status().is2xxSuccessful());
   }

   @Test
   @WithMockUser(roles = "ADMIN")
   public void testSuccessfulRefreshTokenWithAdminRole() throws Exception {

      Authority authority = new Authority();
      authority.setId(1L);
      authority.setName(AuthorityName.ROLE_ADMIN);
      List<Authority> authorities = Arrays.asList(authority);

      User admin = new User();
      admin.setUsername("admin@admin.com");
      admin.setEnabled(Boolean.TRUE);
      admin.setLastPasswordResetDate(Instant.now().plusMillis(1000 * 1000));
      admin.setAuthorities(authorities);

      JwtUserDetails jwtUser = JwtUserDetailsFactory.create(admin);

      when(tokenUtil.getEmailFromToken(any())).thenReturn(admin.getEmail());

      when(userDetailsService.loadUserByUsername(eq(admin.getEmail()))).thenReturn(jwtUser);

      when(tokenUtil.canTokenBeRefreshed(any(), any())).thenReturn(true);

      this.mvc.perform(get(authRefresh)).andExpect(status().is2xxSuccessful());
   }

   @Test
   @WithAnonymousUser
   public void testShouldGetUnauthorizedWithAnonymousUser() throws Exception {
      this.mvc.perform(get(authRefresh)).andExpect(status().isUnauthorized());
   }
}
