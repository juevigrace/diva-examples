plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.user.api.userApiClient)

            implementation(projects.user.database.userDatabaseShared)

            implementation(projects.core.models)
        }
    }
}
