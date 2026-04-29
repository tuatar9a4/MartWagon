package com.devd.build_logic.app

import com.devd.build_logic.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies


/* Hilt 및 KSP 공통 설정 확장 함수 */
fun Project.configHilt() {
    val hiltPluginId = libs.findPlugin("dagger-hilt").get().get().pluginId
    val kspPluginId = libs.findPlugin("ksp").get().get().pluginId

    pluginManager.apply(hiltPluginId)
    pluginManager.apply(kspPluginId)

    dependencies {
        "implementation"(libs.findLibrary("hilt-android").get())
        "implementation"(libs.findLibrary("hilt-compose_nav").get())
        "implementation"(libs.findLibrary("hilt-compose_viewmodel").get())
        "ksp"(libs.findLibrary("hilt-compiler").get())
    }
}