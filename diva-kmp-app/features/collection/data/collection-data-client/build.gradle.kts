plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.collection.api.collectionApiClient)

            implementation(projects.features.collection.database.collectionDatabaseShared)

            implementation(projects.core.models)
        }
    }
}
