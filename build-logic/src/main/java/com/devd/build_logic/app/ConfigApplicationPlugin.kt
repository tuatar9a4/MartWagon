package com.devd.build_logic.app

import com.android.build.api.dsl.ApplicationExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure

fun Project.configApplicationPlugin() {

    extensions.configure<ApplicationExtension>(){
        compileSdk = 37

        defaultConfig {
            targetSdk = 37
            minSdk = 29
        }

        buildFeatures {
            buildConfig = true
            compose = true
        }
    }


    configureKotlinAndroid()

}