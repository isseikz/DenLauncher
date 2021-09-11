package com.example.denlauncher.model

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import java.net.NetworkInterface

class IpAddress(context: Context, private val listener: Listener) {
    interface Listener {
        fun onUpdate(address: String?)
    }

    inner class NetworkCallbackWrapper : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            super.onAvailable(network)
            listener.onUpdate(updateIpAddress())
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            super.onLosing(network, maxMsToLive)
            listener.onUpdate(updateIpAddress())
        }

        override fun onLost(network: Network) {
            super.onLost(network)
            listener.onUpdate(updateIpAddress())
        }

        override fun onUnavailable() {
            super.onUnavailable()
            listener.onUpdate(updateIpAddress())
        }
    }

    private val wrapper = NetworkCallbackWrapper()


    init {
        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()

        (context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager)
            .registerNetworkCallback(request, wrapper)
    }

    private fun updateIpAddress(): String? =
        NetworkInterface.getNetworkInterfaces().let interfaces@{ interfaces ->
            for (networkInterface in interfaces) {
                networkInterface.inetAddresses.asSequence()
                    .filter { it.hostAddress.startsWith("192") }
                    .firstOrNull()
                    ?.let { return@interfaces it }
            }
            null
        }?.hostAddress
}
