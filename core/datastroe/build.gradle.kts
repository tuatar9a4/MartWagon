import com.devd.build_logic.app.configDataStore

plugins {
    id("devd.android.core")
}

android {
    namespace = "com.devd.datastroe"
}

configDataStore()

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation(projects.core.domain)
}