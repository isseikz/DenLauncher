package com.example.denlauncher.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.denlauncher.model.ModelEventListener
import com.example.denlauncher.model.Time

class MainViewModel : ViewModel() {
    val time = MutableLiveData<String>()

    private val timeModelListener = object : ModelEventListener {
        override fun onUpdated(data: Any) {
            if (data is String) {
                time.postValue(data)
            }
        }
    }

    init {
        Time.register(timeModelListener)
    }

    override fun onCleared() {
        super.onCleared()
        Time.unregister(timeModelListener)
    }
}