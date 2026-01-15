plugins {
    id("divabuild.library-server")
}

dependencies {
    api(projects.features.social.database.socialDatabaseShared)

    api(projects.core.database.postgres)
}
