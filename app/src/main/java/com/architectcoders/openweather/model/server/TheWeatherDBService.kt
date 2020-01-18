package com.architectcoders.openweather.model.server

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface TheWeatherDBService {
    @GET("weather")
    fun getLocationWeather(
        @Query("lat") lat :String,
        @Query("lon")  lon: String,
        @Query("APPID")  appid: String): Deferred<WeatherResult>
}