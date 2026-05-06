import com.devd.build_logic.app.configCompose
import com.devd.build_logic.app.configHilt
import com.devd.build_logic.app.configLibraryPlugin
import com.devd.build_logic.app.configNavigation
import gradle.kotlin.dsl.accessors._b47b47dbdc0fcc8c6195eeefe53b59f4.implementation

plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
}


configLibraryPlugin()
configCompose()
configHilt()
configNavigation()

dependencies {
    implementation(project(":core:common"))
    implementation(project(":core:domain"))
}