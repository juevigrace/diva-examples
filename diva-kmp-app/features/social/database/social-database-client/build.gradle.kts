plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.social.database.socialDatabaseShared)

            api(projects.core.database.sqlite)
        }
    }
}
