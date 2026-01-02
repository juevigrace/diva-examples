plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.auth.data.authDataService)

            implementation(projects.session.database.sessionDatabaseServer)

            implementation(libs.koin.core)
        }
    }
}
