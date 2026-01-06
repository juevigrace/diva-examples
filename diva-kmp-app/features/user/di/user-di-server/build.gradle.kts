plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.user.database.userDatabaseServer)
            implementation(projects.features.user.data.userDataService)

            implementation(libs.koin.core)
        }
    }
}
