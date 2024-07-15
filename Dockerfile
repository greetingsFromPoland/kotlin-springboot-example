# syntax=docker/dockerfile:1

## Build stage
FROM openjdk:17-jdk-slim AS build
WORKDIR /app
COPY . .
RUN ls -ls
RUN ./gradlew clean build

## Run stage
FROM openjdk:17-jdk-slim
WORKDIR /application

# Create a user and group
RUN groupadd -r spring && useradd -r -gspring spring
USER spring:spring

# Find the non-plain JAR file and copy it to a known location
COPY --from=build /app/build/libs/application.jar application.jar

# Expose the port
EXPOSE 8080

# Health check
HEALTHCHECK --interval=30s --timeout=30s --start-period=5s --retries=3 \
  CMD curl -f http://localhost:8080/actuator/health || exit 1

# Volume for external configuration
VOLUME /config

# Environment variable to specify the location of external configuration
ENV SPRING_CONFIG_LOCATION=file:/config/

# Signal to gracefully stop the application
STOPSIGNAL SIGTERM

# Command to run the application
CMD ["java", "-jar", "application.jar", "--spring.config.additional-location=${SPRING_CONFIG_LOCATION}"]