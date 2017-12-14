package ma.hiddenfounders.codingchallenge.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;

@Repository
public interface FacebookPhotoRepository extends MongoRepository<FacebookPhoto, String> {

   FacebookPhoto findByImageKey(String imageKey);
}
