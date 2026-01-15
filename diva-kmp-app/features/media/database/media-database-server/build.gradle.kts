plugins {
    id("divabuild.library-server")
}

dependencies {
    api(projects.features.media.database.mediaDatabaseShared)

    api(projects.core.database.postgres)
}
