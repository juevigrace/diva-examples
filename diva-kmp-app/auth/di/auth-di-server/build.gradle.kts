plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.auth.api.authApiHandler)

            implementation(projects.auth.data.authDataServer)

            implementation(projects.auth.database.authDatabaseServer)

            implementation(libs.koin.core)
        }
    }
}
