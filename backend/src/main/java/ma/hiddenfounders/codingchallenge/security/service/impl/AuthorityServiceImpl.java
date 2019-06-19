package ma.hiddenfounders.codingchallenge.security.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ma.hiddenfounders.codingchallenge.security.entity.Authority;
import ma.hiddenfounders.codingchallenge.security.entity.AuthorityName;
import ma.hiddenfounders.codingchallenge.security.repository.IAuthorityRepository;
import ma.hiddenfounders.codingchallenge.security.service.IAuthorityService;

@Service
class AuthorityServiceImpl implements IAuthorityService {

   @Autowired
   private IAuthorityRepository authorityRepository;

   @Override
   public Authority findAuthorityByName(AuthorityName name) {
      return authorityRepository.findByName(name);
   }
}