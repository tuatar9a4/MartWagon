package com.devd.build_logic.app

import com.devd.build_logic.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configDataStore() {

    dependencies {
        "implementation"(libs.findLibrary("androidx-datastore").get())
        "implementation"(libs.findLibrary("androidx-datastore-core").get())
        "implementation"(libs.findLibrary("google-gson").get())
        "implementation"(libs.findLibrary("kotlin-serialization").get())
    }
}