plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.user.database.userDatabaseShared)

            api(projects.core.database.postgres)
        }
    }
}
