package ma.hiddenfounders.codingchallenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;

@Repository
public interface FacebookAlbumRepository extends MongoRepository<FacebookAlbum, String> {

   long countByOwner(String email);
}