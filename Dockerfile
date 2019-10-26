FROM openjdk:8-jdk-alpine
LABEL maintainer="salahmsameh@gmail.com"
COPY target/customer-service.jar app.jar
EXPOSE 8080

ENTRYPOINT ["java", "-Dspring.profiles.active=prod", "-jar", "app.jar"]