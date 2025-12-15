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

rootProject.name = "diva-app"

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
enableFeaturePreview("STABLE_CONFIGURATION_CACHE")

include(
    ":server",
    ":apps:androidApp",
    ":apps:desktopApp",
    ":apps:sharedUI",
)

include(
    ":core:ui",
)

include(
    ":core:database:sqlite",
    ":core:database:postgres",
)

include(
    ":core:models",
    ":core:models:models-api",
)

include(
    "auth:api:auth-api-client",
    "auth:api:auth-api-handler",
    "auth:data:auth-data",
    "auth:data:auth-data-service",
    "auth:data:auth-data-shared",
    "auth:database:auth-database-shared",
    "auth:database:auth-database-client",
    "auth:database:auth-database-server",
    "auth:di:auth-di-client",
    "auth:di:auth-di-server",
    "auth:models:auth-models",
    "auth:models:auth-models-api",
    "auth:models:auth-models-database",
    "auth:ui:auth-ui"
)
