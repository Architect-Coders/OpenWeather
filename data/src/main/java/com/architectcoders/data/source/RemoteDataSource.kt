package com.architectcoders.data.source

import com.architectcoders.domain.Weather

interface RemoteDataSource {
    suspend fun getWeather(lat: String, lon: String, appID: String): Weather
}