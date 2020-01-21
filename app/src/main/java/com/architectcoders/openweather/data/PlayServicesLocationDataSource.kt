package com.architectcoders.openweather.data

import android.annotation.SuppressLint
import android.app.Application
import android.location.Location
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class PlayServicesLocationDataSource(application: Application) :
    com.architectcoders.data.source.LocationDataSource {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(application)


    @SuppressLint("MissingPermission")
    override suspend fun findLat(): String? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.toLat())
                }
        }


    @SuppressLint("MissingPermission")
    override suspend fun findLon(): String? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result.toLon())
                }
        }

    private fun Location?.toLat(): String {
        val addresses = this?.let {
            latitude
        }
        // get integer latitude
        val parts = "$addresses".split(".")
        return parts[0]
    }

    private fun Location?.toLon(): String {
        val addresses = this?.let {
            longitude
        }
        // get integer longitude
        val parts = "$addresses".split(".")
        return parts[0]
    }

}