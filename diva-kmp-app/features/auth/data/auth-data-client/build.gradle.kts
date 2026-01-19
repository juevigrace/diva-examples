plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.auth.api.authApiClient)

            implementation(projects.core.database.databaseShared)

            implementation(projects.core.models)
            implementation(projects.core.models.modelsApi)
        }
    }
}
