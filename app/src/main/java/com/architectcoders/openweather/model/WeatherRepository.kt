package com.architectcoders.openweather.model

import android.app.Activity

class WeatherRepository (activity: Activity) {
    companion object {
        const val LAT = "51"
        const val LON = "0"
        const val CNT = "10"
        const val APP_ID = ""
    }
    //private val apiKey = activity.getString(R.string.api_key)
    private val regionRepository = RegionRepository(activity)

    suspend fun findWeather() =
        WeatherDB.service
            .getWeather(
                LAT,
                LON,
                CNT,
                APP_ID
            )
            .await()
}