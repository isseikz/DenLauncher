package com.example.denlauncher

import android.app.Application
import android.content.Context

class MainApplication : Application() {
    companion object {
        lateinit var applicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        Companion.applicationContext = this
    }
}
