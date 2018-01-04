package ma.hiddenfounders.codingchallenge.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ma.hiddenfounders.codingchallenge.security.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

   User findByEmailOrUsername(String email, String username);
   
   User findByEmail(String email);
}