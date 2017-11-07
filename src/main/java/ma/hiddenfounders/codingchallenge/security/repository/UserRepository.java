package ma.hiddenfounders.codingchallenge.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ma.hiddenfounders.codingchallenge.security.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   @Query("select u from User u where u.email = ?1 or u.username = ?1")
   User findByEmail(String email);
}