package com.architectcoders.openweather.model.server

import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.domain.Weather
import com.architectcoders.openweather.model.toDomainWeather

class WeatherDataSource: RemoteDataSource {
    override suspend fun getWeather(lat: String, lon: String, appID: String): Weather {
        return WeatherDB.service
            .getLocationWeather(lat, lon, appID).await().toDomainWeather()
    }
}