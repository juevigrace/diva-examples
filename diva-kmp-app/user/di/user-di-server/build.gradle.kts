plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.user.database.userDatabaseServer)

            implementation(libs.koin.core)
        }
    }
}
