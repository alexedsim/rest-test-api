# Use an official OpenJDK runtime as a parent image
FROM openjdk:11-jdk-slim

# Set the working directory inside the container
WORKDIR /app

# Copy the packaged Spring Boot application to the container
COPY target/rest-api-demo-0.0.1-SNAPSHOT.jar /app

# Expose the port that the Spring Boot application will be listening on
EXPOSE 8080

# Set environment variables for the Spring Boot application
ENV SPRING_PROFILES_ACTIVE=prod
ENV SPRING_DATASOURCE_URL=jdbc:mysql://mysql-server:3306/betest
ENV SPRING_DATASOURCE_USERNAME=root
ENV SPRING_DATASOURCE_PASSWORD=password
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver
ENV SPRING_JPA_HIBERNATE_DDL-AUTO=update

# Start the Spring Boot application when the container starts
CMD ["java", "-jar", "rest-api-demo-0.0.1-SNAPSHOT.jar"]