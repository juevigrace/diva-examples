plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.session.database.sessionDatabaseShared)

            implementation(projects.core.modelsServer)
        }
    }
}
