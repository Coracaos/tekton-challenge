# ----------- Stage 1: Build -----------
FROM maven:3.8.8-eclipse-temurin-21-alpine AS build
WORKDIR /app

COPY pom.xml .
COPY src src

RUN mvn clean package -P docker -DskipTests

# ----------- Stage 2: Run -----------
FROM eclipse-temurin:21-alpine
LABEL maintainer="Manuel Cardenas <card.edu.10@gmail.com>"
LABEL version="1.0"
LABEL description="Tekton Challenge"

WORKDIR /app

COPY --from=build /app/target/challenge-0.0.1.jar /app/

ENTRYPOINT ["java", "-jar", "/app/challenge-0.0.1.jar"]