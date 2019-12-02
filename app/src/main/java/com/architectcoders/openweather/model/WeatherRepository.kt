package com.architectcoders.openweather.model

import android.app.Activity
import com.architectcoders.openweather.R

class WeatherRepository(activity: Activity) {

    private val regionRepository = RegionRepository(activity)
    private val appID = activity.resources.getString(R.string.key_app)

    suspend fun findWeather() =
        WeatherDB.service
            .getLocationWeather(
                regionRepository.findLat(),
                regionRepository.findLon(),
                appID
            )
            .await()
}