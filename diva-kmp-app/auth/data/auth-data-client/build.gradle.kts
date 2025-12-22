plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.auth.api.authApiClient)

            implementation(projects.session.database.sessionDatabaseShared)

            implementation(projects.core.models)
        }
    }
}
