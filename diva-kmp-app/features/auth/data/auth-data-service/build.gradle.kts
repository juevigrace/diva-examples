plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.session.database.sessionDatabaseShared)

    implementation(projects.core.modelsServer)
    implementation(projects.core.server.util)
}
