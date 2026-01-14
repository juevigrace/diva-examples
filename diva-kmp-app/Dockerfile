# Stage 1: Cache Gradle dependencies
FROM gradle:9.2.1-jdk21 AS cache
WORKDIR /app
ENV GRADLE_USER_HOME=/home/gradle/.gradle
COPY gradle gradle
COPY build.gradle.kts gradle.properties settings.gradle.kts ./
COPY build-logic build-logic

# Core server modules
COPY server server
COPY core/server core/server
COPY core/models-server core/models-server
COPY core/models core/models
COPY core/database/postgres core/database/postgres

# Auth modules
COPY features/auth/api/auth-api-handler features/auth/api/auth-api-handler
COPY features/auth/data/auth-data-service features/auth/data/auth-data-service
COPY features/auth/di/auth-di-server features/auth/di/auth-di-server

# Session modules
COPY features/session/database/session-database-server features/session/database/session-database-server
COPY features/session/database/session-database-shared features/session/database/session-database-shared

# User modules
COPY features/user/api/user-api-handler features/user/api/user-api-handler
COPY features/user/data/user-data-service features/user/data/user-data-service
COPY features/user/database/user-database-server features/user/database/user-database-server
COPY features/user/database/user-database-shared features/user/database/user-database-shared
COPY features/user/di/user-di-server features/user/di/user-di-server

# Verification modules
COPY features/verification/data/verification-data features/verification/data/verification-data
COPY features/verification/database/verification-database features/verification/database/verification-database
COPY features/verification/di/verification-di features/verification/di/verification-di
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
