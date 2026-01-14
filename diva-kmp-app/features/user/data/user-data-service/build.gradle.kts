plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.user.database.userDatabaseShared)

    implementation(projects.core.modelsServer)
}
