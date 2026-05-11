plugins {
    id("devd.android.application")
}

android {
    namespace = "com.devd.martwagon"

    defaultConfig {
        applicationId = "com.devd.martwagon"
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.feature.home)
    implementation(projects.feature.report)
    implementation(projects.feature.setting)
    implementation(projects.feature.tag)
    implementation(projects.feature.record)
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.common)
    implementation(projects.core.datastroe)
    implementation(projects.core.database)
}