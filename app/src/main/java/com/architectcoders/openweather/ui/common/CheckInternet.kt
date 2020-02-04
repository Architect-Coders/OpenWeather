package com.architectcoders.openweather.ui.common

import android.net.ConnectivityManager

class CheckInternet(var connectivityManager: ConnectivityManager) {

    fun isOnline( continuation: (Boolean) -> Unit){
        val netInfo = connectivityManager.activeNetworkInfo
        if(netInfo != null && netInfo.isConnectedOrConnecting){
            continuation(true)
        } else {
            continuation(false)
        }
    }
}