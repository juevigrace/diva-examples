plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.features.media.api.mediaApiClient)

            implementation(projects.features.media.database.mediaDatabaseClient)

            implementation(projects.features.media.data.mediaDataClient)

            implementation(libs.koin.core)
        }
    }
}
