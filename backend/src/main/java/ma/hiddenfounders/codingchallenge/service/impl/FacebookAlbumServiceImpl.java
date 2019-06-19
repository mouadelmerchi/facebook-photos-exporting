package ma.hiddenfounders.codingchallenge.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;
import ma.hiddenfounders.codingchallenge.repository.IFacebookAlbumRepository;
import ma.hiddenfounders.codingchallenge.service.IFacebookAlbumService;

@Service
class FacebookAlbumServiceImpl implements IFacebookAlbumService {

   @Autowired
   private IFacebookAlbumRepository fbAlbumRepository;

   @Override
   public long countFacebookAlbumsByOwner(String ownerEmail) {
      return fbAlbumRepository.countByOwner(ownerEmail);
   }

   @Override
   public Page<FacebookAlbum> getUserFacebookAlbums(FacebookAlbum probe, Pageable pageable) {
      return fbAlbumRepository.findAll(Example.of(probe), pageable);
   }

   @Override
   public FacebookAlbum saveFacebookAlbum(FacebookAlbum album) {
      return fbAlbumRepository.save(album);
   }

   @Override
   public FacebookAlbum getFacebookAlbumById(String id) {
      Optional<FacebookAlbum> albumOpt = fbAlbumRepository.findById(id);
      return albumOpt.orElse(null);
   }
}
