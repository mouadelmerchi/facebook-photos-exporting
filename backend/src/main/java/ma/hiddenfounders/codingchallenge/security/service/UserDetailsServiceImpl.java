package ma.hiddenfounders.codingchallenge.security.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.codingchallenge.security.entity.User;
import ma.hiddenfounders.codingchallenge.security.exception.UserNotEnabledException;
import ma.hiddenfounders.codingchallenge.security.jwt.JwtUserDetailsFactory;
import ma.hiddenfounders.codingchallenge.security.repository.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Service("userDetailsService")
class UserDetailsServiceImpl implements UserDetailsService {

   private static final Logger LOGGER = LoggerFactory.getLogger(UserDetailsService.class);

   @Autowired
   private UserRepository userRepository;

   @Override
   public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      LOGGER.debug("Authenticating {}", email);

      User user = userRepository.findByEmail(email);

      if (user == null) {
         throw new UsernameNotFoundException(String.format("No user found with email '%s'.", email));
      } else if (!user.getEnabled()) {
         throw new UserNotEnabledException(String.format("User '%s' is disabled", email));
      } else {
         return JwtUserDetailsFactory.create(user);
      }
   }
}
