package com.example.denlauncher.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denlauncher.model.InstalledApp
import com.example.denlauncher.model.InstalledApps
import com.example.denlauncher.model.ModelEventListener
import com.example.denlauncher.model.Time

class MainViewModel : ViewModel() {
    val time = MutableLiveData<String>()
    val installedApps = MutableLiveData<List<InstalledApp>>(emptyList())

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
    }

    override fun onCleared() {
        super.onCleared()
        Time.unregister(timeModelListener)
        InstalledApps.unregister(appListListener)
    }
}
