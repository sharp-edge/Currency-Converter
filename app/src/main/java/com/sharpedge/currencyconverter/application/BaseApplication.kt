package com.sharpedge.currencyconverter.application

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()

    }
}
// Last reviewed: 2026-04-29
