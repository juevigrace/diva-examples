import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
    id("divabuild.library-android")
    id("divabuild.targets-ios")
    id("divabuild.targets-jvm")
    id("divabuild.targets-web")
}

version = libs.versions.app.version.name

kotlin {
    sourceSets {
        commonMain.dependencies {
            api(libs.diva.core)
        }
    }
}
