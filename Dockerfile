### Stage 1: Build with Maven + Java 17 ###
FROM maven:3.8.6-eclipse-temurin-17 AS build
WORKDIR /app

# 1) Copy only pom.xml to leverage layer caching for deps
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# 2) Copy source & package
COPY src ./src
RUN mvn clean package -DskipTests -B

### Stage 2: Run the JAR on a slim JDK ###
FROM eclipse-temurin:17-jdk-focal
WORKDIR /app

# Grab the built JAR from the previous stage
COPY --from=build /app/target/GraduationProject-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
