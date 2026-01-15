pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        mavenLocal()
    }
}

rootProject.name = "diva-kmp-app"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

fun shouldIncludeClientProjects(): Boolean {
    return !(providers.gradleProperty("excludeCompose").orNull?.toBoolean() ?: false)
}

// Client projects
if (shouldIncludeClientProjects()) {
    include(
        ":apps:androidApp",
        ":apps:desktopApp",
        ":apps:sharedUI",
    )

    include(":core:database:sqlite")

    include(":core:ui")

    include(
        ":features:auth:api:auth-api-client",
        ":features:auth:data:auth-data-client",
        ":features:auth:di:auth-di-client",
        ":features:auth:ui:auth-ui",
    )

    include(
        ":features:session:database:session-database-client",
    )

    include(
        ":features:user:api:user-api-client",
        ":features:user:data:user-data-client",
        ":features:user:database:user-database-client",
        ":features:user:di:user-di-client",
        ":features:user:ui:user-ui",
    )
}

// Server projects
include(":server")

include(":core:server:util")
include(":core:server:mail")

include(":core:database:postgres")

include(":core:models-server")

include(
    ":features:auth:api:auth-api-handler",
    ":features:auth:data:auth-data-service",
    ":features:auth:di:auth-di-server",
)

include(
    ":features:session:database:session-database-server",
)

include(
    ":features:user:api:user-api-handler",
    ":features:user:data:user-data-service",
    ":features:user:database:user-database-server",
    ":features:user:di:user-di-server",
)

include(
    ":features:verification:data:verification-data",
    ":features:verification:database:verification-database",
    ":features:verification:di:verification-di",
)

// Shared projects
include(
    ":core:models",
    ":core:models:models-shared",
    ":core:models:models-api",
)

include(
    ":features:session:database:session-database-shared",
)

include(
    ":features:user:database:user-database-shared",
)
