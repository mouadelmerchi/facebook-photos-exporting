package ma.hiddenfounders.codingchallenge.rest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * This is a REST controller dedicated to test purposes
 */
@RestController
public class GreetingRestController {

   @RequestMapping("/api/hello")
   @PreAuthorize("hasRole('ADMIN')")
   public String greet() {
      return "Hello from the other side!!!";
   }
}