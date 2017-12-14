package ma.hiddenfounders.codingchallenge.rest;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import ma.hiddenfounders.codingchallenge.common.util.CurrentUser;
import ma.hiddenfounders.codingchallenge.common.util.Utils;
import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetails;

@RestController
@RequestMapping("/api/connect")
public class FacebookRestController {

   private static final Logger LOGGER = LoggerFactory.getLogger(FacebookRestController.class);

   @RequestMapping(value = "/facebook", method = RequestMethod.GET)
   public String facebookConnectionStatus(@CurrentUser JwtUserDetails userDetails) {
      
      LOGGER.info("############# User {} #############", userDetails);
      
      RestTemplate restTemplate = new RestTemplate();

      String url = "http://localhost:8080/connect/{providerId}";
      
      Map<String, String> vars = new HashMap<>();
      vars.put("providerId", "facebook");
      
      String view = restTemplate.getForObject(url, String.class, vars);
      
      LOGGER.info("########### returned view {} #############", view);
      
      return "redirect:/";
   }
   
   @RequestMapping(value = "/facebook", method = RequestMethod.POST)
   public ResponseEntity<String> connectToFacebook(@CurrentUser JwtUserDetails userDetails) {

      LOGGER.info("############# User {} #############", userDetails);

      RestTemplate restTemplate = new RestTemplate();

      String url = "http://localhost:8080/connect/{providerId}";

      // Setting up path to be sent to REST service
      Map<String, String> vars = new HashMap<>();
      vars.put("providerId", "facebook");

      // Setting body objects to be POSTed
      MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
      body.add("scope", Utils.FACEBOOK_SCOPE);

      HttpHeaders headers = new HttpHeaders();
      headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

      HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);

      URI location = restTemplate.postForLocation(url, request, vars);
      headers.setLocation(location);

      LOGGER.info("############ HttpHeaders {}", headers);

      return ResponseEntity.ok().headers(headers).body("Redirect to facebook sign in page");
   }
}
