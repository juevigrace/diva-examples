plugins {
    id("divabuild.library-server")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.features.verification.database.verificationDatabase)

            implementation(projects.core.modelsServer)
        }
    }
}
