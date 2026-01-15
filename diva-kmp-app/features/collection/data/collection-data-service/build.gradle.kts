plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.collection.database.collectionDatabaseShared)

    implementation(projects.core.modelsServer)
}
