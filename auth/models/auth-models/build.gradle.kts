plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.auth.models.authModelsDatabase)
            api(projects.auth.models.authModelsApi)
        }
    }
}
