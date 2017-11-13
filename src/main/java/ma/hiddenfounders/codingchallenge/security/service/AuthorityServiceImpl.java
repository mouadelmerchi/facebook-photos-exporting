package ma.hiddenfounders.codingchallenge.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.codingchallenge.security.entity.Authority;
import ma.hiddenfounders.codingchallenge.security.entity.AuthorityName;
import ma.hiddenfounders.codingchallenge.security.repository.AuthorityRepository;

@Service
public class AuthorityServiceImpl implements AuthorityService {

   @Autowired
   private AuthorityRepository authorityRepository;

   @Override
   public Authority findAuthorityByName(AuthorityName name) {
      return authorityRepository.findByName(name);
   }
}