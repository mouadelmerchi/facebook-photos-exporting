package ma.hiddenfounders.codingchallenge.controller;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/connect")
public class CustomConnectController extends ConnectController {

   public CustomConnectController(ConnectionFactoryLocator connectionFactoryLocator,
         ConnectionRepository connectionRepository ) {
      super(connectionFactoryLocator, connectionRepository);
   }

   @Override
   protected String connectedView(String providerId) {
      return "redirect:/";
   }
}
