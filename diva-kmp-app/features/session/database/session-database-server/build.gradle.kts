plugins {
    id("divabuild.library-server")
}

dependencies {
    api(projects.features.session.database.sessionDatabaseShared)

    api(projects.core.database.postgres)
}
