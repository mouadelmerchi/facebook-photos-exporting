package ma.hiddenfounders.codingchallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;
import ma.hiddenfounders.codingchallenge.repository.FacebookAlbumRepository;

@Service
public class FacebookAlbumServiceImpl implements FacebookAlbumService {

   @Autowired
   private FacebookAlbumRepository fbAlbumRepository;

   @Override
   public Page<FacebookAlbum> findAllFacebookAlbums(Pageable pageable) {
      return fbAlbumRepository.findAll(pageable);
   }

   @Override
   public FacebookAlbum saveFacebookAlbum(FacebookAlbum album) {
      return fbAlbumRepository.save(album);
   }
}
