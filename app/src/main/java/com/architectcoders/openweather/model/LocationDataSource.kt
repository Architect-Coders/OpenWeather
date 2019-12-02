package com.architectcoders.openweather.model

import android.annotation.SuppressLint
import android.app.Activity
import android.location.Location
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

import com.google.android.gms.location.LocationServices

interface LocationDataSource {
    suspend fun findLastLocation(): Location?
}

class PlayServicesLocationDataSource(activity: Activity) : LocationDataSource {
    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity)

    @SuppressLint("MissingPermission")
    override suspend fun findLastLocation(): Location? =
        suspendCancellableCoroutine { continuation ->
            fusedLocationClient.lastLocation
                .addOnCompleteListener {
                    continuation.resume(it.result)
                }
        }
}
