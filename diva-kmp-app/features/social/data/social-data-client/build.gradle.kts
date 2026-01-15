plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.social.api.socialApiClient)

            implementation(projects.features.social.database.socialDatabaseShared)

            implementation(projects.core.models)
        }
    }
}
