plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.features.auth.api.authApiClient)

            implementation(projects.features.auth.data.authDataClient)

            implementation(projects.features.session.database.sessionDatabaseClient)

            implementation(libs.koin.core)
        }
    }
}
