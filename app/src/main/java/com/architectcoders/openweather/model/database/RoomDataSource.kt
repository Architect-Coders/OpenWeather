package com.architectcoders.openweather.model.database

import com.architectcoders.data.LocalDataSource
import com.architectcoders.domain.Weather as DomainWeathear

import com.architectcoders.openweather.model.database.Weather as RoomWeather

class RoomDataSource(db: WeatherDatabase) : LocalDataSource {

    val dao: WeatherDao = db.weatherDao()

    override fun getListWeather(city: String): List<DomainWeathear> {
        return dao.findByCity(city).map(RoomWeather::toDomainWeather)
    }

    override fun saveWeather(weather: DomainWeathear) {
        dao.insertWeather(weather.toRoomWeather())
    }
}

fun DomainWeathear.toRoomWeather(): RoomWeather = RoomWeather(
    id,
    city,
    main,
    description,
    temperature,
    pressure,
    humidity,
    temp_min,
    temp_max,
    timestamp
)

fun RoomWeather.toDomainWeather(): DomainWeathear = DomainWeathear(
    id,
    city,
    main,
    description,
    temperature,
    pressure,
    humidity,
    temp_min,
    temp_max,
    timestamp
)