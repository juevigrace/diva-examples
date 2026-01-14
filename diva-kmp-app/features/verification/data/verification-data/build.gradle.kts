plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.verification.database.verificationDatabase)

    implementation(projects.core.modelsServer)
}
