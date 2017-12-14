package ma.hiddenfounders.codingchallenge.social.web;

import javax.annotation.PostConstruct;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/connect")
public class CustomConnectController extends ConnectController {

   private ConnectInterceptor<Facebook> facebookInterceptor;

   public CustomConnectController(ConnectionFactoryLocator connectionFactoryLocator,
         ConnectionRepository connectionRepository, ConnectInterceptor<Facebook> facebookInterceptor) {
      super(connectionFactoryLocator, connectionRepository);
      this.facebookInterceptor = facebookInterceptor;
   }

   @PostConstruct
   public void addInteceptor() {
      this.addInterceptor(facebookInterceptor);
   }

   @Override
   protected String connectView(String providerId) {
      return redirect();
   }

   @Override
   protected String connectedView(String providerId) {
      return redirect();
   }

   private String redirect() {
      return "redirect:/";
   }
}
