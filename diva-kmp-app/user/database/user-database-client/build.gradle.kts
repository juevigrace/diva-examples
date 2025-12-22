plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.user.database.userDatabaseShared)

            api(projects.core.database.sqlite)
        }
    }
}
