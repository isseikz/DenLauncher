package com.example.denlauncher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

object TimeReceiver : BroadcastReceiver() {
    private val listeners: MutableList<TimeEventListener> = mutableListOf()

    fun register(listener: TimeEventListener) {
        listeners.add(listener)
    }

    fun unregister(listener: TimeEventListener) {
        listeners.remove(listener)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (Intent.ACTION_TIME_CHANGED == it.action) {
                listeners.forEach { listener ->
                    listener.onTimeChanged()
                }
            }
        }
    }
}

interface TimeEventListener {
    fun onTimeChanged()
}