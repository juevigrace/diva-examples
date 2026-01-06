plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.user.api.userApiClient)

            implementation(projects.features.user.database.userDatabaseShared)

            implementation(projects.core.models)
        }
    }
}
