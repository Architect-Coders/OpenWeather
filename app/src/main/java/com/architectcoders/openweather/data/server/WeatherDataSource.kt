package com.architectcoders.openweather.data.server

import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.Weather
import com.architectcoders.openweather.data.toDomainWeather

class WeatherDataSource(private  val weatherDB: WeatherDB): RemoteDataSource {
    override suspend fun getWeather(lat: String, lon: String, appID: String): Weather {
        return weatherDB.service
            .getLocationWeather(lat, lon, appID).await().toDomainWeather()
    }
}