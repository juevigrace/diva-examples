plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.auth.database.authDatabaseShared)

            api(projects.core.database.sqlite)
        }
    }
}
