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

* http://localhost:8002/swagger-ui/index.html
* http://localhost:8002/v3/api-docs/

# Demo App Architecture

![demo-app](https://user-images.githubusercontent.com/42716524/211201373-b0fd0c31-5c82-4d87-ade3-a6f9bc2c9334.png)

#### Created with `http://www.plantuml.com/plantuml/uml/`
URL to decode:
```
//www.plantuml.com/plantuml/png/dLBDIWCn4BxdAORUnmVeeHHKF8cYuANq49EfhcutCfaeHVhkagLhqPcoIsyX-NxopJVBB9wbR5ldWoI6sLEXYxSCeMseanduuUXSQ5E9yEq0LZTUwClly97uimcqTdlN9I6Gz05fez_dS5sOtZrSQgqxZiGPRvl0AGzw0D6BV_QPUj0HTdMuhf3zVrijfQt_UDzpYYN8cFk06x5NQcFPAsFJj3HYVyIDsLVGTFT3E_X0BqqMtgaQ5U1NhRuGzyEiie2uiDR2gQkAj8k2EN0gGZP7JtSogn-AJNBsJn2DVJlr0KHpFquu8-esxJcjei-l5DUuq4Nq2VepiPrXEby7EqF1pZfPO4bTBDls1m00
```
 
 

