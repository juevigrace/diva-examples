plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.media.api.mediaApiClient)

            implementation(projects.features.media.database.mediaDatabaseShared)

            implementation(projects.core.models)
        }
    }
}
