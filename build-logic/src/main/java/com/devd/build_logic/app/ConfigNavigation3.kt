package com.devd.build_logic.app

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.devd.build_logic.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configNavigation() {
    val composePlugin = libs.findPlugin("jetbrains-kotlin-serialization").get().get().pluginId
    pluginManager.apply(composePlugin)

    dependencies {
        "implementation"(libs.findLibrary("androidx-navigation3-runtime").get())
        "implementation"(libs.findLibrary("androidx-navigation3-ui").get())
        "implementation"(libs.findLibrary("androidx-lifecycle-viewmodel-navigation3").get())
        "implementation"(libs.findLibrary("kotlinx-serialization-core").get())
        "implementation"(libs.findLibrary("androidx-material3-adaptive-navigation3").get())
    }
}