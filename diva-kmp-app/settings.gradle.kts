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
        ":auth:api:auth-api-client",
        ":auth:data:auth-data-client",
        ":auth:di:auth-di-client",
        ":auth:ui:auth-ui",
    )

    include(
        ":session:database:session-database-client",
    )

    include(
        ":user:database:user-database-client",
        ":user:di:user-di-client",
        ":user:ui:user-ui",
    )
}

// Server projects
include(":server")

include(":core:server:util")

include(":core:database:postgres")

include(":core:models-server")

include(
    ":auth:api:auth-api-handler",
    ":auth:data:auth-data-service",
    ":auth:di:auth-di-server",
)

include(
    ":session:database:session-database-server",
)

include(
    ":user:database:user-database-server",
    ":user:di:user-di-server",
)

// Shared projects
include(
    ":core:models",
    ":core:models:models-api",
    ":core:models:models-database"
)

include(
    ":session:data:session-data",
    ":session:database:session-database-shared",
)

include(
    ":user:database:user-database-shared",
)
