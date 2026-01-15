plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.chat.api.chatApiClient)

            implementation(projects.features.chat.database.chatDatabaseShared)

            implementation(projects.core.models)
        }
    }
}
