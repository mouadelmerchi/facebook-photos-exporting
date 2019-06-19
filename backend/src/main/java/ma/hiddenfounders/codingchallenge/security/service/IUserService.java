package ma.hiddenfounders.codingchallenge.security.service;

import ma.hiddenfounders.codingchallenge.security.entity.User;
import ma.hiddenfounders.codingchallenge.security.exception.DuplicateUserException;
import ma.hiddenfounders.codingchallenge.security.exception.InvalidUserInfoException;

public interface IUserService {

   User findUserByEmailOrUsername(String email, String username);

   User saveNewUser(User user) throws InvalidUserInfoException, DuplicateUserException;
}
