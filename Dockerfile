FROM eclipse-temurin:21-jre-alpine

ENV PORT=8080

WORKDIR /app

COPY target/*.jar app.jar

EXPOSE ${PORT}

ENTRYPOINT ["java", "-jar", "app.jar"]
