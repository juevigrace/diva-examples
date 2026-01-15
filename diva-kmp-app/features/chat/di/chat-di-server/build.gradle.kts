plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.chat.database.chatDatabaseServer)
    implementation(projects.features.chat.data.chatDataService)

    implementation(libs.koin.core)
}
