plugins {
    id("divabuild.library-server")
}

dependencies {
    api(projects.features.chat.database.chatDatabaseShared)

    api(projects.core.database.postgres)
}
