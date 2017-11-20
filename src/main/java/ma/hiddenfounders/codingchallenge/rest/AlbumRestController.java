package ma.hiddenfounders.codingchallenge.rest;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import ma.hiddenfounders.codingchallenge.dto.AlbumsListResponse;
import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;
import ma.hiddenfounders.codingchallenge.service.FacebookAlbumService;

@RestController
@RequestMapping("/api/facebook")
public class AlbumRestController {

   private static final int PAGE_SIZE = 9;

   private FacebookAlbumService fbAlbumService;

   public AlbumRestController(FacebookAlbumService fbAlbumService) {
      this.fbAlbumService = fbAlbumService;
   }

   @RequestMapping(value = "/albums", method = RequestMethod.GET)
   public ResponseEntity<AlbumsListResponse> fetchUserAlbums() {

      int currentPage = 0;

      Page<FacebookAlbum> albumsPage = fbAlbumService.findAllFacebookAlbums(PageRequest.of(currentPage, PAGE_SIZE));
      if (!albumsPage.hasContent()) {
         return ResponseEntity.ok(null);
      }

      List<FacebookAlbum> albumsList = albumsPage.getContent();

      return ResponseEntity.ok(new AlbumsListResponse(albumsList, currentPage, PAGE_SIZE));
   }
}
