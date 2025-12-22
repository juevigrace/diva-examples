plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.user.database.userDatabaseClient)

            implementation(libs.koin.core)
        }
    }
}
