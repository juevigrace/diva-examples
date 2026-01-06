plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.session.database.sessionDatabaseShared)

            api(projects.core.database.postgres)
        }
    }
}
