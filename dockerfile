FROM eclipse-temurin:21-alpine
LABEL maintaner="Manuel Cardenas <card.edu.10@gmail.com>"
LABEL version="1.0"
LABEL description="Tekton Challenge"
WORKDIR /app
COPY src src
COPY pom.xml .
COPY .mvn .mvn
COPY mvnw .
RUN ./mvnw clean package -P docker -DskipTests
ENTRYPOINT ["java","-jar", "/app/target/challenge-0.0.1.jar"]