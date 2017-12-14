package ma.hiddenfounders.codingchallenge.entity;

import org.springframework.social.facebook.api.Reference;

public class FacebookAlbumReference extends Reference {

   private static final long serialVersionUID = -3081411965498817898L;

   private String owner;

   @SuppressWarnings("unused")
   private FacebookAlbumReference() {
      this(null, null, null);
   }

   public FacebookAlbumReference(String id, String owner) {
      this(id, null, owner);
   }

   public FacebookAlbumReference(String id, String name, String owner) {
      super(id, name);
      this.owner = owner;
   }

   public String getOwner() {
      return owner;
   }
}
