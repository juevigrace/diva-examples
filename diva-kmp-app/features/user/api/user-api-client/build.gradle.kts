plugins {
    id("divabuild.library")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(projects.core.models.modelsApi)

            api(libs.diva.network.client)
        }
    }
}
