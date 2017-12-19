package ma.hiddenfounders.codingchallenge.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;

public interface FacebookAlbumService {

   FacebookAlbum getFacebookAlbumById(String id);
   
   long countFacebookAlbumsByOwner(String ownerEmail);
   
   Page<FacebookAlbum> getUserFacebookAlbums(FacebookAlbum probe, Pageable pageable);
   
   FacebookAlbum saveFacebookAlbum(FacebookAlbum album);
}