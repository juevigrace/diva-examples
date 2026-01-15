plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.features.chat.api.chatApiClient)

            implementation(projects.features.chat.database.chatDatabaseClient)

            implementation(projects.features.chat.data.chatDataClient)

            implementation(libs.koin.core)
        }
    }
}
