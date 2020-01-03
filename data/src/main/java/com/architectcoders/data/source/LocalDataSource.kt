package com.architectcoders.data.source

import com.architectcoders.domain.Weather

interface LocalDataSource {
    fun getListWeather(city: String): List<Weather>
    fun saveWeather(weather: Weather)
    fun findByTimestamp(timestamp: String): Weather
    fun findByCity(city: String): List<Weather>
}