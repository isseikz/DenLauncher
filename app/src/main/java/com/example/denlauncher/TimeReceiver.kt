package com.example.denlauncher

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter

class TimeReceiver : BroadcastReceiver() {
    companion object {
        val ACTIONS_LIST = listOf(
            Intent.ACTION_TIMEZONE_CHANGED,
            Intent.ACTION_TIME_TICK,
            Intent.ACTION_TIME_CHANGED
        )
    }

    private val listeners: MutableList<TimeEventListener> = mutableListOf()

    fun register(listener: TimeEventListener) {
        listeners.add(listener)
    }

    fun unregister(listener: TimeEventListener) {
        listeners.remove(listener)
    }

    fun startBroadcasting(context: Context) {
        IntentFilter().also {
            ACTIONS_LIST.forEach { action ->
                it.addAction(action)
            }
            context.registerReceiver(this, it)
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let {
            if (ACTIONS_LIST.contains(it.action)) {
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
