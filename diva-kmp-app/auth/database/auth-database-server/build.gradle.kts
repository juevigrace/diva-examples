plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.auth.database.authDatabaseShared)

            api(projects.core.database.postgres)
        }
    }
}
