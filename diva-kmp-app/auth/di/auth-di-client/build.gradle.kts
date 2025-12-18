plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }
        commonMain.dependencies {
            implementation(projects.auth.api.authApiClient)

            implementation(projects.auth.data.authDataClient)

            implementation(projects.auth.database.authDatabaseClient)

            implementation(libs.koin.core)
        }
    }
}
