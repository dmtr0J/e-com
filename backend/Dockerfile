FROM eclipse-temurin:21-alpine

WORKDIR /app

COPY target/*.war app.war

ENTRYPOINT ["java", "-jar", "app.war", "--spring.profiles.active=develop"]
