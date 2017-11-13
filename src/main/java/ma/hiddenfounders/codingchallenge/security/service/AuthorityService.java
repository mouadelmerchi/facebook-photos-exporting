package ma.hiddenfounders.codingchallenge.security.service;

import ma.hiddenfounders.codingchallenge.security.entity.Authority;
import ma.hiddenfounders.codingchallenge.security.entity.AuthorityName;

public interface AuthorityService {

   Authority findAuthorityByName(AuthorityName name);
}
