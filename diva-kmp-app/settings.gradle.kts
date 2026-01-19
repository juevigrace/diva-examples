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

    include(":core:database:database-client")

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

    include(
        ":features:app:home:data:home-data",
        ":features:app:home:di:home-di",
        ":features:app:home:ui:home-ui",
    )

    include(
        ":features:app:library:data:library-data",
        ":features:app:library:di:library-di",
        ":features:app:library:ui:library-ui",
    )

    include(
        ":features:app:profile:data:profile-data",
        ":features:app:profile:di:profile-di",
        ":features:app:profile:ui:profile-ui",
    )

    include(
        ":features:app:settings:data:settings-data",
        ":features:app:settings:di:settings-di",
        ":features:app:settings:ui:settings-ui",
    )

    include(
        ":features:media:api:media-api-client",
        ":features:media:data:media-data-client",
        ":features:media:database:media-database-client",
        ":features:media:di:media-di-client",
        ":features:media:ui:media-ui",
    )

    include(
        ":features:collection:api:collection-api-client",
        ":features:collection:data:collection-data-client",
        ":features:collection:database:collection-database-client",
        ":features:collection:di:collection-di-client",
        ":features:collection:ui:collection-ui",
    )

    include(
        ":features:chat:api:chat-api-client",
        ":features:chat:data:chat-data-client",
        ":features:chat:database:chat-database-client",
        ":features:chat:di:chat-di-client",
        ":features:chat:ui:chat-ui",
    )

    include(
        ":features:social:api:social-api-client",
        ":features:social:data:social-data-client",
        ":features:social:database:social-database-client",
        ":features:social:di:social-di-client",
        ":features:social:ui:social-ui",
    )
}

// Server projects
include(":server")

include(":core:server:util")
include(":core:server:mail")

include(":core:database:database-server")

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

include(
    ":features:media:api:media-api-handler",
    ":features:media:data:media-data-service",
    ":features:media:database:media-database-server",
    ":features:media:di:media-di-server",
)

include(
    ":features:collection:api:collection-api-handler",
    ":features:collection:data:collection-data-service",
    ":features:collection:database:collection-database-server",
    ":features:collection:di:collection-di-server",
)

include(
    ":features:chat:api:chat-api-handler",
    ":features:chat:data:chat-data-service",
    ":features:chat:database:chat-database-server",
    ":features:chat:di:chat-di-server",
)

include(
    ":features:social:api:social-api-handler",
    ":features:social:data:social-data-service",
    ":features:social:database:social-database-server",
    ":features:social:di:social-di-server",
)

// Shared projects
include(
    ":core:database:database-shared"
)

include(
    ":core:models",
    ":core:models:models-shared",
    ":core:models:models-api",
)
