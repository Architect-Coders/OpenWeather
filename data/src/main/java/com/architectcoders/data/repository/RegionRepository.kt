package com.architectcoders.data.repository

import com.architectcoders.data.source.LocationDataSource

class RegionRepository(
    private val locationDataSource: LocationDataSource,
    private val permissionChecker: PermissionChecker
) {

    companion object {
        const val DEFAULT_LAT = "0"
        const val DEFAULT_LON = "0"
    }

    suspend fun findLat(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLat() ?: DEFAULT_LAT
        } else {
            DEFAULT_LAT
        }
    }

    suspend fun findlon(): String {
        return if (permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)) {
            locationDataSource.findLon() ?: DEFAULT_LON
        } else {
            DEFAULT_LON
        }
    }
}


interface PermissionChecker {

    enum class Permission { COARSE_LOCATION }

    suspend fun check(permission: Permission): Boolean
}