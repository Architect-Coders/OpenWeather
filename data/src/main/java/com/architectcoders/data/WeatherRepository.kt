package com.architectcoders.data

import com.architectcoders.domain.Weather

class WeatherRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource,
    private val regionRepository: RegionRepository,
    private val appID: String
) {

    fun getWeather(): Weather {
        val weather = remoteDataSource.getWeather(
            regionRepository.findLat(),
            regionRepository.findlon(),
            appID
        )
        localDataSource.saveWeather(weather)
        return weather
    }

}

interface LocalDataSource {
    fun getListWeather(city: String): List<Weather>
    fun saveWeather(weather: Weather)
}

interface RemoteDataSource {
    fun getWeather(lat: String, lon: String, appID: String): Weather
}