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

   private static final String RESOURCE_PATH = "/user";
   
   private MockMvc mvc;

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

   @Test
   public void testShouldGetUnauthorizedWithoutRole() throws Exception {
      mvc.perform(get("/user")).andExpect(status().isUnauthorized());
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
      user.setEnabled(true);
      user.setLastPasswordResetDate(Instant.now().plusMillis(1000 * 1000));
      user.setAuthorities(authorities);

      JwtUserDetails jwtUser = JwtUserDetailsFactory.create(user);

      when(tokenUtil.getEmailFromToken(any())).thenReturn(user.getEmail());

      when(userDetailsService.loadUserByUsername(eq(user.getEmail()))).thenReturn(jwtUser);

      mvc.perform(get(RESOURCE_PATH).header("Authorization", "Bearer nsodunsodiuv")).andExpect(status().is2xxSuccessful());
   }

}