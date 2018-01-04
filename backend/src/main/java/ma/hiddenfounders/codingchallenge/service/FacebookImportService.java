package ma.hiddenfounders.codingchallenge.service;

public interface FacebookImportService {

   void importFacebookAlbums(String userEmail, String albumsPath, Integer defaultPageSize, String imagesExt);
}
