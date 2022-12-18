# demo-app-products-microservice

Just a demo project.
A microservice for the products of my demo spring application.
A user can read / write / update / delete products.

# Java
It requires Java version 17

# Build the App

```
./mvnw clean package
./mvnw spring-boot:run
 ```

### Run with test profile
```
./mvnw spring-boot:run -Dspring-boot.run.profiles=test
```


### Or with docker
 ```
 docker-compose up --build

 ```

# Database
This app is with postgres configured. See application.properties.

If you need a database local you can use
 ```
 docker-compose up -d products-database
  ```

Or use the "test" spring profile:

```SPRING_PROFILES_ACTIVE=test ```

With this profile a h2 databse will be used.

# Api Docu

* http://localhost:8080/swagger-ui/index.html
* http://localhost:8080//v3/api-docs/
 
 

