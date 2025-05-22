# Build java application
FROM maven:3.9-eclipse-temurin-21-alpine AS build

WORKDIR /app

COPY . .

# RUN mvn clean package -DskipTests
RUN mvn clean package -Dmaven.test.skip=true


# Build docker container
FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
