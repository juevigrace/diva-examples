plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.social.database.socialDatabaseShared)

    implementation(projects.core.modelsServer)
}
