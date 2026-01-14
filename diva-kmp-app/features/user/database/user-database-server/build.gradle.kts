plugins {
    id("divabuild.library-server")
}

dependencies {
    api(projects.features.user.database.userDatabaseShared)

    api(projects.core.database.postgres)
}
