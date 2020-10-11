package com.vs.trendz.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.core.content.ContextCompat.getSystemService

class NetworkStatus(val context: Context) {


    companion object {

        @Volatile
        var instance: NetworkStatus? = null

        fun getInstance(context: Context): NetworkStatus {
            return instance ?: synchronized(this) {
                instance ?: NetworkStatus(context).also { instance = it }
            }
        }

    }

    fun connectionAvailable(): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    }

}