plugins {
    id("divabuild.library")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.auth.models.authModelsApi)

            implementation(projects.core.models.modelsApi)

            api(libs.diva.network.client)
        }
    }
}
