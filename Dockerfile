FROM gradle:8.4.0-jdk21 AS build
WORKDIR /home/gradle/project

COPY build.gradle gradlew ./
COPY gradle ./gradle
RUN chmod +x gradlew
RUN ./gradlew dependencies --no-daemon || true

COPY . .
RUN ./gradlew clean build --no-daemon -x test

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
