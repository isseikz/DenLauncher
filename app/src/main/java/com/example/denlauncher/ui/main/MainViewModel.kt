package com.example.denlauncher.ui.main

import android.content.Context
import android.net.ConnectivityManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denlauncher.MainApplication
import com.example.denlauncher.model.*

class MainViewModel : ViewModel() {
    val time = MutableLiveData<String>()
    val ipAddress = MutableLiveData<String>()
    val installedApps = MutableLiveData<List<InstalledApp>>(emptyList())
    private val ipAddressListener: IpAddress

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

    init {
        Time.register(timeModelListener)
        InstalledApps.register(appListListener)

        val manager =
            MainApplication.applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        ipAddressListener = IpAddress(manager, object : IpAddress.Listener {
            override fun onUpdate(address: String) {
                ipAddress.postValue(address)
            }
        })
    }

    override fun onCleared() {
        super.onCleared()
        Time.unregister(timeModelListener)
        InstalledApps.unregister(appListListener)
    }

    fun onClicked(app: InstalledApp, context: Context) {
        context.startActivity(context.packageManager.getLaunchIntentForPackage(app.packageName))
    }
}
