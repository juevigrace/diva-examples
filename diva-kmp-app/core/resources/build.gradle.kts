plugins {
    id("divabuild.library-kmp-base")
    id("divabuild.library-targets")
    id("divabuild.setup-compose-multiplatform")
    id("divabuild.serialization")
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(libs.compose.components.resources)
            implementation(libs.compose.runtime)
        }
    }
}

compose.resources {
    generateResClass = always
    publicResClass = true
}
