plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.media.api.mediaApiClient)

            implementation(projects.core.database.databaseShared)

            implementation(projects.core.models)
        }
    }
}
