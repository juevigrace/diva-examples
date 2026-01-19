plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.database.databaseShared)

            api(projects.core.database.databaseClient)
        }
    }
}
