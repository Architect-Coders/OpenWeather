package com.architectcoders.data.source

import com.architectcoders.domain.Weather

interface LocalDataSource {
    suspend fun getListWeather(city: String): List<Weather>
    suspend fun saveWeather(weather: Weather)
    suspend fun findByTimestamp(timestamp: String): Weather
    suspend fun findByCity(city: String): List<Weather>
}