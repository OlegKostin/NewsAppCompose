plugins {
    id("java-library")
    alias(libs.plugins.jetbrains.kotlin.jvm)
    alias(libs.plugins.kotlinSerialization)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17
    }
}

dependencies{

    implementation(libs.retrofit)
    implementation(libs.kotlin.coroutine.core)
    implementation(libs.kotlin.serialization.json)
    implementation(libs.androidx.annotation) // annotations
    implementation(libs.retrofit2.kotlinx.serialization.converter)
    implementation (libs.retrofit.adapters.result)
}