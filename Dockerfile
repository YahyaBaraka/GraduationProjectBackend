### Stage 1: Build with Maven + JDK 17 ###
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1) Copy only pom.xml to leverage layer caching for dependencies
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# 2) Copy source & package the application
COPY src ./src
RUN mvn clean package -DskipTests -B

### Stage 2: Run the JAR on a slim JDK 17 runtime, listening on port 8081 ###
FROM eclipse-temurin:17-jdk-slim
WORKDIR /app

# Copy the packaged JAR from the build stage
COPY --from=build /app/target/GraduationProject-0.0.1-SNAPSHOT.jar app.jar

# Tell Spring Boot to listen on 8081
ENV SERVER_PORT=8081

# Expose port 8081
EXPOSE 8081

# Define a Docker-level health check using Spring Actuator's default endpoint
HEALTHCHECK --interval=10s --timeout=3s --start-period=5s \
  CMD curl -f http://localhost:${SERVER_PORT}/actuator/health || exit 1

# Launch the Spring Boot application on the configured port
ENTRYPOINT ["java", "-Dserver.port=${SERVER_PORT}", "-jar", "app.jar"]
