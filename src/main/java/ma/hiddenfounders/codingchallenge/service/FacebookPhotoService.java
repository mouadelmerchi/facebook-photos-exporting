package ma.hiddenfounders.codingchallenge.service;

import java.io.IOException;

import ma.hiddenfounders.codingchallenge.entity.FacebookPhoto;

public interface FacebookPhotoService {

   FacebookPhoto saveFacebookPhoto(FacebookPhoto photo) throws IOException;
}
