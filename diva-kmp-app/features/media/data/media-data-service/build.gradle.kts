plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.media.database.mediaDatabaseShared)

    implementation(projects.core.modelsServer)
}
