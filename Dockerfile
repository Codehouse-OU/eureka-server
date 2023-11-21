# Stage 1: Build the application
FROM eclipse-temurin:21-alpine AS build

# Set the working directory in Docker
WORKDIR /home/gradle/project

# Copy the Gradle configuration files
COPY build.gradle settings.gradle dependencies.gradle ./

# Copy the Gradle Wrapper and other necessary files
COPY gradlew ./
COPY gradle gradle

# Copy the project source
COPY src src

# Build the application
RUN ./gradlew build --no-daemon

# Stage 2: Extract layers
FROM eclipse-temurin:21-alpine AS layers

# Set the working directory
WORKDIR /application

# Copy the built jar from the build stage
COPY --from=build /home/gradle/project/build/libs/*.jar application.jar

# Extract layers
RUN java -Djarmode=layertools -jar application.jar extract

# Stage 3: Create the runtime image
FROM eclipse-temurin:21-jre-alpine

# Set the deployment directory
WORKDIR /app

# Copy layers from the layers stage
COPY --from=layers /application/dependencies/ ./
COPY --from=layers /application/spring-boot-loader/ ./
COPY --from=layers /application/snapshot-dependencies/ ./
COPY --from=layers /application/application/ ./

# Run the application as a non-root user
USER 1001

# Expose the port
EXPOSE 8761

# Set the entry point to launch the application
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]
