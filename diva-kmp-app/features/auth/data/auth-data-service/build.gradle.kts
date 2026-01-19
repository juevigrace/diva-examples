plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.core.database.databaseShared)

    implementation(projects.core.modelsServer)
    implementation(projects.core.server.util)
}
