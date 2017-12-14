package ma.hiddenfounders.codingchallenge.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;
import ma.hiddenfounders.codingchallenge.repository.FacebookAlbumRepository;

@Service
class FacebookAlbumServiceImpl implements FacebookAlbumService {

   @Autowired
   private FacebookAlbumRepository fbAlbumRepository;

   @Override
   public long countFacebookAlbumsByOwner(String ownerEmail) {
      return fbAlbumRepository.countByOwner(ownerEmail);
   }

   @Override
   public Page<FacebookAlbum> findUserFacebookAlbums(FacebookAlbum probe, Pageable pageable) {
      return fbAlbumRepository.findAll(Example.of(probe), pageable);
   }

   @Override
   public FacebookAlbum saveFacebookAlbum(FacebookAlbum album) {
      return fbAlbumRepository.save(album);
   }
}
