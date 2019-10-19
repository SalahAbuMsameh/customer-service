FROM openjdk:8-jdk-alpine
LABEL maintainer="salahmsameh@gmail.com"
COPY target/customer-service.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-Djava.security.egd=file:/dev/./urandom", "-jar", "app.jar"]
