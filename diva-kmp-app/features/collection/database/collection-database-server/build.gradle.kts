plugins {
    id("divabuild.library-server")
}

dependencies {
    api(projects.features.collection.database.collectionDatabaseShared)

    api(projects.core.database.postgres)
}
