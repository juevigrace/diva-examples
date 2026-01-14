plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.verification.database.verificationDatabase)
    implementation(projects.features.verification.data.verificationData)

    implementation(libs.koin.core)
}
