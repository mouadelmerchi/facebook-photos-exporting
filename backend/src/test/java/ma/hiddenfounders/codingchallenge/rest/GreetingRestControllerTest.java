package ma.hiddenfounders.codingchallenge.rest;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import ma.hiddenfounders.codingchallenge.security.util.TokenUtil;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GreetingRestControllerTest {

   private static final String RESOURCE_PATH = "/api/hello";

   private MockMvc mvc;

   @Autowired
   private WebApplicationContext context;

   @MockBean
   private TokenUtil tokenUtil;

   @Before
   public void setup() {
      mvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
   }

   @Test
   public void testShouldGetUnauthorizedWithoutRole() throws Exception {
      this.mvc.perform(get(RESOURCE_PATH)).andExpect(status().isUnauthorized());
   }

   @Test
   @WithMockUser(roles = "USER")
   public void testShouldGetForbiddenWithUserRole() throws Exception {
      this.mvc.perform(get(RESOURCE_PATH)).andExpect(status().isForbidden());
   }

   @Test
   @WithMockUser(roles = "ADMIN")
   public void testShouldGetOkWithAdminRole() throws Exception {
      this.mvc.perform(get(RESOURCE_PATH)).andExpect(status().is2xxSuccessful());
   }

    @Test
   @WithMockUser(roles = "ADMIN")
   public void testShouldGetOkWithAdminRoleM() throws Exception {
      this.mvc.perform(get(RESOURCE_PATH)).andExpect(status().is2xxSuccessful());
   }

}