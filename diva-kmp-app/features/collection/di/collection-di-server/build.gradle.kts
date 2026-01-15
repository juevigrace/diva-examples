plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.collection.database.collectionDatabaseServer)
    implementation(projects.features.collection.data.collectionDataService)

    implementation(libs.koin.core)
}
