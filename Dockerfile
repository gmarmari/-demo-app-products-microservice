FROM openjdk:17-slim-bullseye

LABEL org.opencontainers.image.authors="Georgios Marmaris"

VOLUME ["/app/log"]

ENTRYPOINT ["java", "-jar", "/app/products.jar"]

COPY target/products-*.jar /app/products.jar