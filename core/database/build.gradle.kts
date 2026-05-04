import com.devd.build_logic.app.configRoomDB

plugins {
    id("devd.android.core")
}

android {
    namespace = "com.devd.database"
}

configRoomDB()

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}