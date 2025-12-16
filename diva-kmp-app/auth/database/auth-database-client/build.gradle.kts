plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.auth.database.authDatabaseShared)

            implementation(projects.core.database.sqlite)
        }
    }
}
