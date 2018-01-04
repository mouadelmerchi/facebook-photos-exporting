package ma.hiddenfounders.codingchallenge.security.service;

import java.util.Arrays;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.codingchallenge.security.entity.Authority;
import ma.hiddenfounders.codingchallenge.security.entity.AuthorityName;
import ma.hiddenfounders.codingchallenge.security.entity.User;
import ma.hiddenfounders.codingchallenge.security.exception.DuplicateUserException;
import ma.hiddenfounders.codingchallenge.security.exception.InvalidUserInfoException;
import ma.hiddenfounders.codingchallenge.security.repository.UserRepository;
import ma.hiddenfounders.codingchallenge.security.util.TimeProvider;

@Service
class UserServiceImpl implements UserService {

   @Autowired
   private UserRepository userRepository;

   @Autowired
   private AuthorityService authorityService;

   @Autowired
   private PasswordEncoder passwordEncoder;

   @Autowired
   private TimeProvider timeProvider;

   public User findUserById(Long id) {
      return userRepository.getOne(id);
   }
   
   @Override
   public User findUserByEmailOrUsername(String email, String username) {
      return userRepository.findByEmailOrUsername(email, username);
   }

   @Override
   public User saveNewUser(User user) throws InvalidUserInfoException, DuplicateUserException {
      if (isDuplicate(user)) {
         throw new DuplicateUserException("Duplicate user information");
      }

      user.setId(0L);
      user.setPassword(passwordEncoder.encode(user.getPassword()));
      user.setEnabled(true);
      user.setLastPasswordResetDate(timeProvider.now());
      Authority userAuthority = authorityService.findAuthorityByName(AuthorityName.ROLE_USER);
      user.setAuthorities(Arrays.asList(userAuthority));

      try {
         return userRepository.saveAndFlush(user);
      } catch (ConstraintViolationException e) {
         throw new InvalidUserInfoException("Invalid fields");
      }
   }

   private boolean isDuplicate(User user) {
      User searchedUser = findUserByEmailOrUsername(user.getEmail(), user.getUsername());
      return (searchedUser != null);
   }
}
