import divabuild.internal.libs

plugins {
    id("divabuild.library-base")
    id("org.jetbrains.kotlin.jvm")
    `java-library`
}

dependencies {
    implementation(libs.diva.core)
}
