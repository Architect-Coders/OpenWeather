package com.architectcoders.openweather.model

import com.architectcoders.openweather.R
import com.architectcoders.openweather.WeatherApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import com.architectcoders.openweather.model.database.Weather as DbWeather
import com.architectcoders.openweather.model.WeatherResult as ServerWeather

class WeatherRepository(application: WeatherApp) {

    private val regionRepository = RegionRepository(application)
    private val appID = application.resources.getString(R.string.key_app)
    private val db = application.db

    suspend fun findWeather(): DbWeather = withContext(Dispatchers.IO) {
        with(db.weatherDao()) {
            val dbWeather = WeatherDB.service
                .getLocationWeather(
                    regionRepository.findLat(),
                    regionRepository.findLon(),
                    appID
                )
                .await().convertToDbWeather()
            insertWeather(dbWeather)
            dbWeather
        }
    }

    suspend fun findByTimestamp(timestamp: String): DbWeather = withContext(Dispatchers.IO) {
        db.weatherDao().findByTimestamp(timestamp)
    }

    suspend fun findByCity(city: String): List<DbWeather> = withContext(Dispatchers.IO) {
        db.weatherDao().findByCity(city)
    }

}

private fun ServerWeather.convertToDbWeather() = DbWeather(
    0,
    name,
    weather[0].main,
    weather[0].description,
    "${main.temp}",
    "${main.pressure}",
    "${main.humidity}",
    "${main.tempMin}",
    "${main.tempMax}",
    (System.currentTimeMillis() / 1000).toString()
)
