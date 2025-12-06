import divabuild.internal.libs

plugins {
    id("divabuild.library-base")
    id("divabuild.setup-compose-multiplatform")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.diva.ui)
        }
    }
}
