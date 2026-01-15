plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.chat.database.chatDatabaseShared)

            api(projects.core.database.sqlite)
        }
    }
}
