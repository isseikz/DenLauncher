package com.example.denlauncher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class TimeReceiver : BroadcastReceiver() {
    private val listeners: MutableList<TimeEventListener> = mutableListOf()

    fun register(listener: TimeEventListener) {
        listeners.add(listener)
    }

    fun unregister(listener: TimeEventListener) {
        listeners.remove(listener)
    }

    fun startBroadcasting(context: Context) {
        context.registerReceiver(this, IntentFilter("android.intent.action.TIME_SET"))
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