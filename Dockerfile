FROM amazoncorretto:21-alpine-jdk

WORKDIR /app

COPY /app/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
