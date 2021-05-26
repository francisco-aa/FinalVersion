package fr.fscript98.zapette.autre

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.NET_CAPABILITY_INTERNET

class ConnectivityCallback : ConnectivityManager.NetworkCallback() {
    override fun onCapabilitiesChanged(network: Network , capabilities: NetworkCapabilities) {
        val connected = capabilities.hasCapability(NET_CAPABILITY_INTERNET)
    }
    override fun onLost(network: Network) {

    }
}