plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.chat.database.chatDatabaseShared)

    implementation(projects.core.modelsServer)
}
