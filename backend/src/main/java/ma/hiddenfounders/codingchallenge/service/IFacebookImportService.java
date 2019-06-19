package ma.hiddenfounders.codingchallenge.service;

public interface IFacebookImportService {

   void importFacebookAlbums(String userEmail, String albumsPath, Integer defaultPageSize, String imagesExt);
}
