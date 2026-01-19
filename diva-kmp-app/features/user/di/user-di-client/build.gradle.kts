plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.core.database.databaseShared)

            implementation(projects.features.user.api.userApiClient)

            implementation(projects.features.user.database.userDatabaseClient)

            implementation(projects.features.user.data.userDataClient)

            implementation(libs.koin.core)
        }
    }
}
