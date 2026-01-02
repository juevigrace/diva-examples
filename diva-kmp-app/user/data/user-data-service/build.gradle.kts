plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.user.database.userDatabaseShared)

            implementation(projects.core.modelsServer)
        }
    }
}
