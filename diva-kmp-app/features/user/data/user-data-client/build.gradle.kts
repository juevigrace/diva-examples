plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.user.api.userApiClient)

            implementation(projects.core.database.databaseShared)
            implementation(projects.core.database.databaseShared)

            implementation(projects.core.models)
        }
    }
}
