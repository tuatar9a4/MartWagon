package com.devd.build_logic.app

import com.devd.build_logic.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

fun Project.configRoomDB() {

    val kspPluginId = libs.findPlugin("ksp").get().get().pluginId

    pluginManager.apply(kspPluginId)

    dependencies {
        "implementation"(libs.findLibrary("androidx-room").get())
        "implementation"(libs.findLibrary("androidx-room-ktx").get())
        "ksp"(libs.findLibrary("androidx-room-compiler").get())
    }
}