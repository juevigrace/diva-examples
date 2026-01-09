plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.auth.api.authApiClient)

            implementation(projects.features.session.database.sessionDatabaseShared)

            implementation(projects.core.models)
            implementation(projects.core.models.modelsApi)
        }
    }
}
