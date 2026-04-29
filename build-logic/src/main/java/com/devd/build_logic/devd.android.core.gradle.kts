import com.devd.build_logic.app.configApplicationPlugin
import com.devd.build_logic.app.configHilt
import com.devd.build_logic.app.configLibraryPlugin
import com.devd.build_logic.app.configureKotlinAndroid

plugins {
    id("com.android.library")
}


configLibraryPlugin()
configHilt()