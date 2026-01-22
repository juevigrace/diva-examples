plugins {
    id("divabuild.library-server")
}

dependencies {
    api(projects.core.database.databaseServer)
}
