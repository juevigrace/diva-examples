plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.permissions.api.permissionsApiClient)

            implementation(projects.core.database.databaseShared)

            implementation(projects.core.models)
        }
    }
}
