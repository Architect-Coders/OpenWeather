package com.architectcoders.openweather.model.database

import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.openweather.model.toDomainWeather
import com.architectcoders.openweather.model.toRoomWeather
import com.architectcoders.domain.Weather as DomainWeathear

import com.architectcoders.openweather.model.database.Weather as RoomWeather

class RoomDataSource(db: WeatherDatabase) :
    LocalDataSource {

    val dao: WeatherDao = db.weatherDao()

    override fun getListWeather(city: String): List<DomainWeathear> {
        return dao.findByCity(city).map(RoomWeather::toDomainWeather)
    }

    override fun saveWeather(weather: DomainWeathear) {
        dao.insertWeather(weather.toRoomWeather())
    }

    override fun findByTimestamp(timestamp: String): com.architectcoders.domain.Weather {
        return dao.findByTimestamp(timestamp).toDomainWeather()
    }

    override fun findByCity(city: String): List<com.architectcoders.domain.Weather> {
        return dao.findByCity(city).map { it.toDomainWeather() }
    }
}
