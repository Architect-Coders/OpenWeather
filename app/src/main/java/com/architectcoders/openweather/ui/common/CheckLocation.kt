package com.architectcoders.openweather.ui.common

import android.location.LocationManager

class CheckLocation(var locationManager: LocationManager) {

    fun checkLocation( continuation: (Boolean) -> Unit) {

        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }
        if (!gpsEnabled && !networkEnabled) {
            continuation(false)
        } else {
            continuation(true)
        }
    }
}