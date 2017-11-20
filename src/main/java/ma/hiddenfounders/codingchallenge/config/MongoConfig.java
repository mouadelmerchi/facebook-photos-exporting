package ma.hiddenfounders.codingchallenge.config;

import javax.annotation.Resource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig {

   private static final String MONGO_DB_HOST_PROPERTY_NAME     = "datasource.nosql.mongodb.host";
   private static final String MONGO_DB_PORT_PROPERTY_NAME     = "datasource.nosql.mongodb.port";
   private static final String MONGO_DB_DATABASE_PROPERTY_NAME = "datasource.nosql.mongodb.database";

   @Resource
   private Environment environment;

   @Bean
   public MongoClient mongo() throws Exception {
      int port;
      try {
         port = Integer.parseInt(environment.getRequiredProperty(MONGO_DB_PORT_PROPERTY_NAME));
      } catch (NumberFormatException e) {
         port = 27017;
      }

      return new MongoClient(environment.getRequiredProperty(MONGO_DB_HOST_PROPERTY_NAME), port);
   }

   @Bean
   public MongoTemplate mongoTemplate() throws Exception {
      return new MongoTemplate(mongo(), environment.getRequiredProperty(MONGO_DB_DATABASE_PROPERTY_NAME));
   }
}
