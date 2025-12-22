plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.session.database.sessionDatabaseShared)

            api(projects.core.database.sqlite)
        }
    }
}
