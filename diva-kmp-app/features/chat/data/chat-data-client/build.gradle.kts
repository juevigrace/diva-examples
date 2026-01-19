plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.chat.api.chatApiClient)

            implementation(projects.core.database.databaseShared)

            implementation(projects.core.models)
        }
    }
}
