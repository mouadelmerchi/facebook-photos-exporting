package ma.hiddenfounders.codingchallenge.security.util;

import java.io.Serializable;
import java.time.Instant;

public class TimeProvider implements Serializable {

   private static final long serialVersionUID = -17647715798313568L;

   public Instant now() {
      return Instant.now();
   }
}
