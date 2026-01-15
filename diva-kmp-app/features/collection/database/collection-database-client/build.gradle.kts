plugins {
    id("divabuild.library")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.features.collection.database.collectionDatabaseShared)

            api(projects.core.database.sqlite)
        }
    }
}
