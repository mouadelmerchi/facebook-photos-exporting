# Facebook Photos Import Coding Challenge

![Screenshot from running application](etc/screenshot-facebook-photos-import-coding-challenge.PNG?raw=true "Screenshot Facebook Photos Import Coding Challenge")

## About

This is just a demo for using **Spring boot** with **Spring Security**, **MongoDB/MySQL** and
**Angular 5**. It consists of an application that lets the user import photos from facebook and 
store them in a NoSQL database.

The project contains two separate parts:

 backend: containing the spring boot project
 frontend: containing the angular project

## Requirements

This demo is built with with Java 1.8.

## Usage

To check out the project and build from source, do the following:

 (`git clone https://github.com/mouadelmerchi/facebook-photos-exporting.git`)
 (`cd facebook-photos-exporting`)
 
If you want to use Eclipse IDE, you can generate Eclipse metadata (.classpath and .project files) with the following:

 (`./mvnw eclipse:eclipse`) for Linux/MacOS users or (`mvnw.cmd eclipse:eclipse`) for windows users

Once complete, you may then import the projects into Eclipse as usual:

 (`File -> Import -> Maven/Existing Maven projects`)
 
"spring-social-facebook" API needs to be adapted to the latest facebook graph APIs which is "v11" at the
the time of making this application. I've made some modifications in the source code so the code won't break
when using the API.

To install the modified version of "spring-social-facebook" to the local repository, do the following:

 (`./mvnw install:install-file -Dfile=etc/lib/spring-social-facebook-3.0.0.M3.jar -DgroupId=org.springframework.social -DartifactId=spring-social-facebook -Dversion=3.0.0.M3 -Dpackaging=jar`)
 
 for Linux/MacOS users
 
or

 (`mvnw.cmd install:install-file -Dfile=etc/spring-social-facebook-3.0.0.M3.jar -DgroupId=org.springframework.social -DartifactId=spring-social-facebook -Dversion=3.0.0.M3 -Dpackaging=jar`) 
 
 for windows users
 
To install the application do the following: 
  
 (`./mvnw clean install`) for Linux/MacOS users or (`mvnw.cmd clean install`) for windows users

Start the application with the Spring Boot maven plugin: 
 
 (`./mvnw -f backend spring-boot:run`) for Linux/MacOS users or (`mvnw.cmd -f backend spring-boot:run`) for windows users

The application should be running at [http://localhost:8080](http://localhost:8080).

For the sake of the demonstration, there are three user accounts that present the different levels of access to the endpoints in
the API and the different authorization exceptions:
```
Admin - admin@admin.com:admin
User - enabled@user.com:user
Disabled - disabled@user.com:disabled (this user is disabled)
```

There are different endpoints that are reasonable for the demo:
```
/auth/... - authentication endpoints
/api/facebook/... - endpoints that are restricted to authorized users (a valid JWT token must be present in the request header)
```

### Databases

Actually this demo is using an embedded H2 database for authentication which is configured by default in the development environment. 
If you want to connect to another database in production environment, just set *default* keyword in front 
of *prod profile* and remove it from *dev profile*, then specify the connection in the *application.yml* file in the resource directory. 
Here is an example for a MySQL DB:

```
spring:
  profiles: prod,*default*
  datasource: 
    url: jdbc:mysql://localhost:3306/FacebookPhotosSecurityDB
    username: root
    password: 
    driverClassName: com.mysql.jdbc.Driver
    defaultSchema:
    maxPoolSize: 20
    hibernate:
      hbm2ddl.method: update
      show_sql: true
      format_sql: true
      dialect: org.hibernate.dialect.MySQL5InnoDBDialect
```

The application requires a MongoDB database. You must specify the connection in *application.yml* to a MongoDB instance, like this:

```
datasource:
  nosql:
    mongodb:
      host: localhost
      port: 27017
      database: test
```

## Creator

**Mouad El Merchichi**

## Copyright and license

The code is released under the [MIT license](LICENSE?raw=true).