package ma.hiddenfounders.codingchallenge.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import ma.hiddenfounders.codingchallenge.entity.FacebookAlbum;

@Repository
public interface FacebookAlbumRepository extends PagingAndSortingRepository<FacebookAlbum, String> {

}