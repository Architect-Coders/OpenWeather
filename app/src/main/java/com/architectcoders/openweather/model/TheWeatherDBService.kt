package com.architectcoders.openweather.model

import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface TheWeatherDBService {
    @GET("find/")
    fun getWeather(
    @Query("lat") lat :String,
    @Query("lon")  lon: String,
    @Query("cnt")  cnt:String,
    @Query("appid")  appid: String): Deferred<WeatherResult>

}