package com.example.mubarak_ansari_practicals.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import java.util.Locale

object Constant {

    const val BASE_URL = "https://api.stackexchange.com/2.3/"

    fun isNetworkConnectionAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities =
            connectivityManager.getNetworkCapabilities(network) ?: return false

        // Check if the active network has one of the supported transports (WiFi, Cellular, Ethernet).
        return when {
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }

    fun convertNumberToReadableFormat(value: Int): String {
        return when {
            value >= 1_000_000 -> {
                val formattedValue = value / 1_000_000.0
                String.format(Locale.getDefault(), "%.2fM", formattedValue)
            }
            value >= 1_000 -> {
                val formattedValue = value / 1_000.0
                String.format(Locale.getDefault(), "%.1fK", formattedValue)
            }
            else -> value.toString()
        }
    }

}