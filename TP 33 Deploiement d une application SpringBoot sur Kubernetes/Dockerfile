# Use Eclipse Temurin JDK 17 on Alpine Linux for minimal image size
FROM eclipse-temurin:17-jdk-alpine

# Metadata labels
LABEL maintainer="MOHAMMED AMINE ELHARCHI <mohammedamineelharchi@outlook.fr>"
LABEL description="Cloud Microservice Platform - Spring Boot on Kubernetes"
LABEL version="1.0.0"
LABEL author="MOHAMMED AMINE ELHARCHI"

# Set working directory
WORKDIR /application

# Copy compiled JAR file from target directory
COPY target/cloud-microservice-platform-2.0.0-RELEASE.jar application.jar

# Expose application port
EXPOSE 8081

# Start the application
ENTRYPOINT ["java", "-jar", "application.jar"]