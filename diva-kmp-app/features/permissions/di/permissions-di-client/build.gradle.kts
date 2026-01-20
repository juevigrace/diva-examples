plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.features.permissions.api.permissionsApiClient)

            implementation(projects.features.permissions.database.permissionsDatabaseClient)

            implementation(projects.features.permissions.data.permissionsDataClient)

            implementation(libs.koin.core)
        }
    }
}
