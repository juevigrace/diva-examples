plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.collection.api.collectionApiClient)

            implementation(projects.core.database.databaseShared)

            implementation(projects.core.models)
        }
    }
}
