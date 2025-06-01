# Use OpenJDK 21 as the base image
FROM openjdk:21-jdk-slim

WORKDIR /app

COPY build/libs/threadpilot-vehicle-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "app.jar"]

