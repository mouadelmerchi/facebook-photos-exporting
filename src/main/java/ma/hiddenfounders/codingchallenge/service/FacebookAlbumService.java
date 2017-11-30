package ma.hiddenfounders.codingchallenge.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;

public interface FacebookAlbumService {

   long countAllFacebookAlbums();
   
   Page<FacebookAlbum> findAllFacebookAlbums(Pageable pageable);
   
   FacebookAlbum saveFacebookAlbum(FacebookAlbum album);
}
