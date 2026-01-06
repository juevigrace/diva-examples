plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.user.database.userDatabaseShared)

            implementation(projects.core.modelsServer)
        }
    }
}
