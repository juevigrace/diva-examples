plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.auth.database.authDatabaseShared)
            api(projects.auth.models.authModels)

            api(projects.core.models)
        }
    }
}
