import com.devd.build_logic.app.configApplicationPlugin
import com.devd.build_logic.app.configCompose
import com.devd.build_logic.app.configHilt
import com.devd.build_logic.app.configNavigation
import com.devd.build_logic.app.configureKotlinAndroid

plugins {
    id("com.android.application")
}


configApplicationPlugin()
configCompose()
configHilt()
configNavigation()

dependencies {
    implementation(project(":core:common"))
}
