plugins {
    id("divabuild.library-server")
}

dependencies {
    implementation(projects.features.permissions.database.permissionsDatabaseServer)
    implementation(projects.features.permissions.data.permissionsDataService)

    implementation(libs.koin.core)
}
