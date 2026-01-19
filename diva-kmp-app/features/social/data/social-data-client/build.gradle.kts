plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.social.api.socialApiClient)

            implementation(projects.core.database.databaseShared)

            implementation(projects.core.models)
        }
    }
}
