package com.example.denlauncher.ui.main

import android.content.Context
import android.content.Intent
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denlauncher.MainApplication
import com.example.denlauncher.model.*

class MainViewModel : ViewModel() {
    val time = MutableLiveData<String>()
    val ipAddress = MutableLiveData<String>()
    val installedApps = MutableLiveData<List<InstalledApp>>(emptyList())
    private val ipAddressModel: IpAddress

    private val timeModelListener = object : ModelEventListener {
        override fun onUpdated(data: Any) {
            if (data is String) {
                time.postValue(data)
            }
        }
    }

    private val appListListener = object : ModelEventListener {
        override fun onUpdated(data: Any) {
            if (data is List<*>) {
                val mutableList = mutableListOf<InstalledApp>()
                data.forEach {
                    if (it is InstalledApp) {
                        mutableList.add(it)
                    }
                }
                installedApps.postValue(mutableList.toList())
            }
        }
    }

    private val ipAddressListener = object : IpAddress.Listener {
        override fun onUpdate(address: String?) {
            val text = address?.let { "Connected: $it" } ?: "Disconnected"
            ipAddress.postValue(text)
        }
    }

    init {
        Time.register(timeModelListener)
        InstalledApps.register(appListListener)

        ipAddressModel = IpAddress(MainApplication.applicationContext, ipAddressListener)
    }

    override fun onCleared() {
        super.onCleared()
        Time.unregister(timeModelListener)
        InstalledApps.unregister(appListListener)
    }

    fun onClicked(context: Context) {
        context.startActivity(Intent(android.provider.Settings.ACTION_SETTINGS))
    }

    fun onClicked(app: InstalledApp, context: Context) {
        context.startActivity(context.packageManager.getLaunchIntentForPackage(app.packageName))
    }
}
