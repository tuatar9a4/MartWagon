package com.devd.build_logic.app

import com.android.build.api.dsl.LibraryExtension
import com.devd.build_logic.libs
import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.plugins.JavaPluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

fun Project.configureKotlinAndroid() {

    pluginManager.run {
        apply("org.jetbrains.kotlin.plugin.compose")
    }

    extensions.configure<JavaPluginExtension> {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11

    }

    extensions.configure<KotlinProjectExtension> {
        jvmToolchain(11)
    }

    configureKotlin()

    val libs = project.libs
    dependencies {
        "implementation"(libs.findLibrary("androidx-core-ktx").get())
        "implementation"(libs.findLibrary("androidx-lifecycle-runtime-ktx").get())
        "implementation"(libs.findLibrary("logger-timber").get())
    }
}


internal fun Project.configureKotlin() {
    tasks.withType<KotlinCompile>().configureEach {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
            val warningAsErrors: String? by project
            allWarningsAsErrors.set(warningAsErrors.toBoolean())
            freeCompilerArgs.addAll("-Xopt-in=kotlin.RequiresOptIn")
        }
    }
}