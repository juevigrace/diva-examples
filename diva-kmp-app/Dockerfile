# Stage 1: Cache Gradle dependencies
FROM gradle:9.2.1-jdk21 AS cache
WORKDIR /app
ENV GRADLE_USER_HOME=/home/gradle/.gradle
COPY gradle gradle
COPY build.gradle.kts gradle.properties settings.gradle.kts ./
COPY build-logic build-logic
COPY server server
COPY auth/api/auth-api-handler auth/api/auth-api-handler
COPY auth/data/auth-data-server auth/data/auth-data-service
COPY auth/data/auth-data-shared auth/data/auth-data-shared
COPY auth/database/auth-database-server auth/database/auth-database-server
COPY auth/database/auth-database-shared auth/database/auth-database-shared
COPY auth/di/auth-di-server auth/di/auth-di-server
COPY auth/models auth/models
COPY core/models core/models
COPY core/database/postgres core/database/postgres
RUN gradle dependencies -PexcludeCompose=true --no-daemon

# Stage 2: Build Application
FROM cache AS build
RUN gradle :server:buildFatJar -PexcludeCompose=true --no-daemon

# Stage 3: Create the Runtime Image
FROM amazoncorretto:21-alpine AS runtime
WORKDIR /app
RUN addgroup -g 1000 appgroup && adduser -u 1000 -G appgroup -s /bin/sh -D diva
RUN mkdir -p ./bin
COPY --from=build /app/server/build/libs/diva-ktor-server.jar ./bin/diva-ktor-server.jar
USER diva
