plugins {
    id("divabuild.library")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.models.modelsApi)

            api(libs.diva.network.client)
        }
    }
}
