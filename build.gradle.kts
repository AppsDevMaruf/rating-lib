import com.chalo.buildsrc.Modules

plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlinSerialization)
}

apply(from = "$rootDir/base-module.gradle")

android {
    namespace = "com.chalo.ratting"
}

dependencies {
    implementation(project(Modules.CORE))
}
