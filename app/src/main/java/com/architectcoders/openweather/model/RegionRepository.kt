package com.architectcoders.openweather.model

import android.Manifest
import android.app.Application
import android.location.Location

class RegionRepository (application: Application) {

    private val locationDataSource = PlayServicesLocationDataSource(application)
    private val coarsePermissionChecker = PermissionChecker(
        application,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    suspend fun findLat(): String = findLastLocation().toLat()
    suspend fun findLon(): String = findLastLocation().toLon()

    private suspend fun findLastLocation(): Location? {
        val success = coarsePermissionChecker.check()
        return if (success) locationDataSource.findLastLocation() else null
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