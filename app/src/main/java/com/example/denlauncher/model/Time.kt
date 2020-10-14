package com.example.denlauncher.model

import android.content.Context
import com.example.denlauncher.TimeEventListener
import com.example.denlauncher.TimeReceiver
import java.text.SimpleDateFormat
import java.util.*

object Time {
    private val timeReceiver = TimeReceiver()
    private val modelListeners: MutableList<ModelEventListener> = mutableListOf()
    private var listener: TimeEventListener = object : TimeEventListener {
        override fun onTimeChanged() {
            updateTime()
        }
    }

    fun register(modelEventListener: ModelEventListener) {
        if (modelListeners.size == 0) {
            timeReceiver.register(listener)
        }
        modelListeners.add(modelEventListener)
        updateTime()
    }

    fun startReceiver(context: Context) {
        timeReceiver.startBroadcasting(context)
    }

    fun unregister(listener: ModelEventListener) {
        modelListeners.remove(listener)
        if (modelListeners.size == 0) {
            releaseListener()
        }
    }

    private fun updateTime() {
        modelListeners.forEach { it.onUpdated(getCurrentTime()) }
    }

    private fun getCurrentTime(): String {
        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN).time.let {
            SimpleDateFormat("HH:mm", Locale.JAPAN).also { format ->
                format.timeZone = TimeZone.getTimeZone("Japan")
            }.format(it)
        }
    }

    private fun releaseListener() {
        timeReceiver.unregister(listener)
    }
}