package ma.hiddenfounders.codingchallenge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;

@Repository
public interface FacebookPhotoRepository extends PagingAndSortingRepository<FacebookPhoto, String> {
   
}
