plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.user.api.userApiClient)

            implementation(projects.user.database.userDatabaseClient)

            implementation(projects.user.data.userDataClient)

            implementation(libs.koin.core)
        }
    }
}
