package com.devd.build_logic.app

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.LibraryExtension
import com.devd.build_logic.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configCompose() {
    val composePlugin = libs.findPlugin("kotlin-compose").get().get().pluginId
    pluginManager.apply(composePlugin)


    extensions.apply {
        when (this) {
            is ApplicationExtension -> buildFeatures { compose = true }
            is LibraryExtension -> buildFeatures { compose = true }

        }

    }

    dependencies {
        "implementation"(libs.findLibrary("androidx-activity-compose").get())
        "implementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
        "implementation"(libs.findLibrary("androidx-compose-ui").get())
        "implementation"(libs.findLibrary("androidx-compose-ui-graphics").get())
        "implementation"(libs.findLibrary("androidx-compose-ui-tooling-preview").get())
        "implementation"(libs.findLibrary("androidx-compose-material3").get())

//        "implementation"(libs.findLibrary("coil-compose").get())
//        "implementation"(libs.findLibrary("coil-video").get())
//        "implementation"(libs.findLibrary("coil-network-okhttp").get())

        "androidTestImplementation"(platform(libs.findLibrary("androidx-compose-bom").get()))
        "androidTestImplementation"(libs.findLibrary("androidx-compose-ui-test-junit4").get())

        "debugImplementation"(libs.findLibrary("androidx-compose-ui-tooling").get())
        "debugImplementation"(libs.findLibrary("androidx-compose-ui-test-manifest").get())
    }
}