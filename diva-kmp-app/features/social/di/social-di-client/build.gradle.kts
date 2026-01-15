plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.features.social.api.socialApiClient)

            implementation(projects.features.social.database.socialDatabaseClient)

            implementation(projects.features.social.data.socialDataClient)

            implementation(libs.koin.core)
        }
    }
}
