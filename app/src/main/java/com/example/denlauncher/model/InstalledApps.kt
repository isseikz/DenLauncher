package com.example.denlauncher.model

import android.content.Context
import android.content.Intent

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
        val intent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }
        apps = context.packageManager.queryIntentActivities(intent, 0).map {
            InstalledApp(
                it.loadLabel(context.packageManager).toString(),
                it.activityInfo.packageName
            )
        }
        notifyData()
    }

    private fun notifyData() {
        modelEventListeners.forEach {
            it.onUpdated(apps)
        }
    }
}
