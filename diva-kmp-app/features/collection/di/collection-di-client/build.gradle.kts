plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        androidMain.dependencies {
            implementation(libs.koin.android)
        }

        commonMain.dependencies {
            implementation(projects.features.collection.api.collectionApiClient)

            implementation(projects.features.collection.database.collectionDatabaseClient)

            implementation(projects.features.collection.data.collectionDataClient)

            implementation(libs.koin.core)
        }
    }
}
