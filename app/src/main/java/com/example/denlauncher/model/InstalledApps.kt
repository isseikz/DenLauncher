package com.example.denlauncher.model

import android.content.Context
import android.content.pm.ApplicationInfo

object InstalledApps {
    private val modelEventListeners: MutableList<ModelEventListener> = mutableListOf()
    private var apps: List<InstalledApp> = emptyList()

    fun register(listener: ModelEventListener) {
        modelEventListeners.add(listener)
        notifyData()
    }

    fun unregister(listener: ModelEventListener) {
        modelEventListeners.remove(listener)
    }

    fun updateList(context: Context) {
        apps = context.packageManager.getInstalledPackages(0).filter {
            it.applicationInfo.flags != ApplicationInfo.FLAG_SYSTEM
        }.map {
            InstalledApp(it.applicationInfo.loadLabel(context.packageManager).toString())
        }
        notifyData()
    }

    private fun notifyData() {
        modelEventListeners.forEach {
            it.onUpdated(apps)
        }
    }
}
