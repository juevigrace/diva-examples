import divabuild.internal.libs

plugins {
    id("divabuild.kmp-base")
    id("com.android.kotlin.multiplatform.library")
}

kotlin {
    androidLibrary {
        val newPath = project.path.split(":").last().replace("-", ".")
        val cleanPath = newPath.let { path ->
            val mutList = path.split(".").toMutableList()
            if (mutList.contains("api") && mutList.contains("client")) {
                return@let mutList.joinToString(".")
            }

            if (mutList.last() == "client") {
                mutList.removeLast()
            }
            mutList.joinToString(".")
        }

        namespace = "com.diva.$cleanPath"

        compileSdk = libs.versions.android.compileSdk.get().toInt()

        minSdk = libs.versions.android.minSdk.get().toInt()

        withJava()
        compilerOptions {
            jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_21)
        }
    }
}
