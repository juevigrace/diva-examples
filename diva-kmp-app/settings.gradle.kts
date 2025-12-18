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
        "auth:api:auth-api-client",
        "auth:data:auth-data-client",
        "auth:database:auth-database-client",
        "auth:di:auth-di-client",
        "auth:ui:auth-ui"
    )
}

// Server projects
include(":server")

include(":core:database:postgres")

include(
    "auth:api:auth-api-handler",
    "auth:data:auth-data-server",
    "auth:database:auth-database-server",
    "auth:di:auth-di-server",
)

// Shared projects
include(
    ":core:models",
    ":core:models:models-api",
    ":core:models:models-database"
)

include(
    "auth:data:auth-data-shared",
    "auth:database:auth-database-shared",
)
