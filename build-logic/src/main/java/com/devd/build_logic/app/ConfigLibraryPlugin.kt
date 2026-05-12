package com.devd.build_logic.app

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configLibraryPlugin() {

    extensions.configure<LibraryExtension>(){
        compileSdk = 37

        defaultConfig {
            minSdk = 29

        }
        buildFeatures {
            buildConfig = true
            compose = true
        }

    }


    configureKotlinAndroid()
}