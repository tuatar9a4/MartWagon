package com.devd.martwagon

import android.app.Application
import com.devd.martwagon.utils.DebugTree
import com.devd.martwagon.utils.ReleaseTree
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class MartWagonApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(DebugTree())
        else Timber.plant(ReleaseTree())
    }
}