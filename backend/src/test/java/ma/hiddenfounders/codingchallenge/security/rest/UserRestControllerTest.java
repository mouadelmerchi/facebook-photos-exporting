package ma.hiddenfounders.codingchallenge.security.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ma.hiddenfounders.codingchallenge.security.entity.Authority;
import ma.hiddenfounders.codingchallenge.security.entity.AuthorityName;
import ma.hiddenfounders.codingchallenge.security.entity.User;
import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetails;
import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetailsFactory;
import ma.hiddenfounders.codingchallenge.security.util.TokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRestControllerTest {

   private MockMvc mvc;
   
   @Value("/${jwt.route.authentication.path}/user")
   private String authUserPath;

   @Autowired
   private WebApplicationContext context;

   @MockBean
   private TokenUtil tokenUtil;

   @MockBean
   private UserDetailsService userDetailsService;

   @Before
   public void setup() {
      mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
   }

//   @Test
   public void testShouldGetUnauthorizedWithoutRole() throws Exception {
      this.mvc.perform(get(authUserPath)).andExpect(status().isUnauthorized());
   }

   @Test
   @WithMockUser(roles = "USER")
   public void testGetUserSuccessfullyWithUserRole() throws Exception {
      Authority authority = new Authority();
      authority.setId(1L);
      authority.setName(AuthorityName.ROLE_ADMIN);
      List<Authority> authorities = Arrays.asList(authority);

      User user = new User();
      user.setEmail("user@provider.com");
      user.setEnabled(Boolean.TRUE);
      user.setLastPasswordResetDate(Instant.now().plusMillis(1000 * 1000));
      user.setAuthorities(authorities);

      JwtUserDetails jwtUser = JwtUserDetailsFactory.create(user);

      when(this.tokenUtil.getEmailFromToken(any())).thenReturn(user.getEmail());

      when(this.userDetailsService.loadUserByUsername(eq(user.getEmail()))).thenReturn(jwtUser);

      this.mvc.perform(get(authUserPath).header("Authorization", "Bearer nsodunsodiuv")).andExpect(status().is2xxSuccessful());
   }


   @Test
   @WithMockUser(roles = "USER")
   public void testGetUserSuccessfullyWithUserRoleM() throws Exception {
      // Code Here
   }

}