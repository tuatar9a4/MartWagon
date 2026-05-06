import com.devd.build_logic.app.configHilt
import com.devd.build_logic.app.configLibraryPlugin

plugins {
    id("com.android.library")
    kotlin("plugin.serialization")
}


configLibraryPlugin()
configHilt()