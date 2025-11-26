FROM gradle:8.4.0-jdk21 AS build
WORKDIR /home/gradle/project

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN ./gradlew dependencies --no-daemon || true

COPY . .
RUN ./gradlew clean build -x test --no-daemon

FROM eclipse-temurin:21-jdk-alpine
WORKDIR /app

COPY --from=build /home/gradle/project/build/libs/*SNAPSHOT.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
