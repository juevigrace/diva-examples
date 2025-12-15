# Stage 1: Cache Gradle dependencies
FROM gradle:9.2.1-jdk21 AS cache
WORKDIR /app
ENV GRADLE_USER_HOME=/home/gradle/.gradle
COPY gradlew gradlew
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts gradle.properties ./
COPY build-logic build-logic
COPY libs.versions.toml ./
RUN ./gradlew dependencies --no-daemon

# Stage 2: Build Application
FROM cache AS build
COPY server server
COPY auth auth
COPY core core
RUN ./gradlew :server:fatJar --no-daemon

# Stage 3: Create the Runtime Image
FROM amazoncorretto:21-alpine AS runtime
WORKDIR /app
RUN addgroup -g 1000 appgroup && adduser -u 1000 -G appgroup -s /bin/sh -D diva
RUN mkdir -p ./bin
COPY --from=build /app/server/build/libs/diva-ktor-server.jar ./bin/diva-ktor-server.jar
USER diva
