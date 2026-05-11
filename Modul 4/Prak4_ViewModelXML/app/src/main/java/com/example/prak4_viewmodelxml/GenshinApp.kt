package com.example.prak4_viewmodelxml

import android.app.Application
import timber.log.Timber

class GenshinApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Inisialisasi Timber hanya pada build debug
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
