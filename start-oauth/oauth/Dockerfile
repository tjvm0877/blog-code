# Build stage
FROM eclipse-temurin:21 AS build
WORKDIR /app
COPY gradlew /app/
COPY gradle /app/gradle
COPY build.gradle /app/
COPY settings.gradle /app/
COPY src /app/src
RUN ./gradlew build -x test --no-daemon

# Runtime stage
FROM openjdk:21-slim AS production
EXPOSE 8080

RUN groupadd -r appgroup && useradd -r -g appgroup -m appuser
RUN mkdir /app && chown appuser:appgroup /app

COPY --from=build /app/build/libs/*.jar /app/app.jar

USER appuser

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
