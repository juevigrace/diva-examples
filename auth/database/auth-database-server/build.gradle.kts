plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.auth.database.authDatabaseShared)

            implementation(projects.core.database.postgres)
        }
    }
}
