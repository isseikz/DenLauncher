package com.example.denlauncher.model

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import java.net.NetworkInterface

class IpAddress(manager: ConnectivityManager, private val listener: Listener) {
    interface Listener {
        fun onUpdate(address: String)
    }

    private val callback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            NetworkInterface.getNetworkInterfaces().let interfaces@{ interfaces ->
                for (networkInterface in interfaces) {
                    networkInterface.inetAddresses.asSequence()
                        .filter { it.hostAddress.startsWith("192") }
                        .firstOrNull()
                        ?.let { return@interfaces it }
                }
                null
            }?.hostAddress?.let {
                listener.onUpdate("Connected to $it")
            } ?: run { listener.onUpdate("Not connected") }
        }
    }

    init {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        manager.registerNetworkCallback(request, callback)
    }
}
