package com.architectcoders.openweather.model

import android.app.Application
import com.architectcoders.openweather.R

class WeatherRepository(application: Application) {

    private val regionRepository = RegionRepository(application)
    private val appID = application.resources.getString(R.string.key_app)

    suspend fun findWeather() =
        WeatherDB.service
            .getLocationWeather(
                regionRepository.findLat(),
                regionRepository.findLon(),
                appID
            )
            .await()
}