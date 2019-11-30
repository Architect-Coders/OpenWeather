package com.architectcoders.openweather.model

import android.app.Activity
import com.architectcoders.openweather.R

class WeatherRepository(activity: Activity) {
    companion object {
        const val LAT = "51"
        const val LON = "0"
    }

    //private val apiKey = activity.getString(R.string.api_key)
    private val regionRepository = RegionRepository(activity)
    private val appID = activity.resources.getString(R.string.key_app)

    suspend fun findWeather() =
        WeatherDB.service
            .getLocationWeather(
                LAT,
                LON,
                appID
            )
            .await()
}