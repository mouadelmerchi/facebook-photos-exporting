package ma.hiddenfounders.codingchallenge.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.hiddenfounders.codingchallenge.security.entity.Authority;
import ma.hiddenfounders.codingchallenge.security.entity.AuthorityName;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

   Authority findByName(AuthorityName name);
}
