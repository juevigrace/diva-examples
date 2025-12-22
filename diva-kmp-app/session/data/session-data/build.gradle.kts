plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.session.database.sessionDatabaseShared)

            implementation(projects.core.models)
        }
    }
}
