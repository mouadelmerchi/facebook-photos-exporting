package ma.hiddenfounders.codingchallenge.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingRestController {
   
   @RequestMapping("/api/hello")
   public String greet() {
      return "Hello from the other side!!!";
   }
}