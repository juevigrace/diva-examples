plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.user.database.userDatabaseServer)
            implementation(projects.user.data.userDataService)

            implementation(libs.koin.core)
        }
    }
}
