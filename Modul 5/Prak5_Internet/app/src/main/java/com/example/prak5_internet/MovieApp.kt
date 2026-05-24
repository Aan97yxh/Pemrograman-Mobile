package com.example.prak5_internet

import android.app.Application
import timber.log.Timber

class MovieApp : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}