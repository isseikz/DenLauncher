package com.example.denlauncher.model

import android.content.Context

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
//        apps = context.packageManager.getInstalledApplications(PackageManager.GET_META_DATA).map {
//            InstalledApp(it.name)
//        }
        apps = listOf(
            "App1",
            "App2",
            "App3"
        ).map { InstalledApp(it) }
        notifyData()
    }

    fun notifyData() {
        modelEventListeners.forEach {
            it.onUpdated(apps)
        }
    }
}