plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.user.database.userDatabaseShared)

            api(projects.core.database.sqlite)
        }
    }
}
