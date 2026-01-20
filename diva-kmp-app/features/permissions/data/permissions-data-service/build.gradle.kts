plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.core.database.databaseShared)

    implementation(projects.core.modelsServer)
}
