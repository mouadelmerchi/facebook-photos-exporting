package ma.hiddenfounders.codingchallenge.controller;

import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.api.Album;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.PagedList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;
import ma.hiddenfounders.codingchallenge.service.FacebookAlbumService;

@Controller
@RequestMapping("/connect")
public class CustomConnectController extends ConnectController {

   private Facebook             facebook;
   private FacebookAlbumService fbAlbumService;

   public CustomConnectController(ConnectionFactoryLocator connectionFactoryLocator,
         ConnectionRepository connectionRepository, Facebook facebook, FacebookAlbumService fbAlbumService) {
      super(connectionFactoryLocator, connectionRepository);
      this.facebook = facebook;
      this.fbAlbumService = fbAlbumService;
   }

   @Override
   protected String connectedView(String providerId) {
      return importAlbumsAndRedirect(providerId);
   }

   private String importAlbumsAndRedirect(String providerId) {
      if ("facebook".equals(providerId)) {
         PagedList<Album> albums = facebook.mediaOperations().getAlbums();

         albums.forEach(album -> {
            FacebookAlbum fbAlbum = new FacebookAlbum(album.getId(), album.getName(), album.getCount(),
                  album.getCoverPhoto().getId(), album.getType());

            System.out.println("###### " + fbAlbum + " ######");

            fbAlbumService.saveFacebookAlbum(fbAlbum);

            // TODO: save each album's photos
         });
      }

      return "redirect:/";
   }
}
