package com.architectcoders.openweather.model

import android.Manifest
import android.app.Activity
import android.location.Geocoder
import android.location.Location

class RegionRepository (activity: Activity) {

    private val locationDataSource = PlayServicesLocationDataSource(activity)
    private val coarsePermissionChecker = PermissionChecker(
        activity,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    suspend fun findLat(): String = findLastLocation().toLat()
    suspend fun findLon(): String = findLastLocation().toLon()

    private suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.request()
        return if (success) locationDataSource.findLastLocation() else null
    }

    private fun Location?.toLat(): String {
        val addresses = this?.let {
            latitude
        }
        return "$addresses".substring(0..1)
    }

    private fun Location?.toLon(): String {
        val addresses = this?.let {
            longitude
        }
        return "$addresses".substring(0..1)
    }

}