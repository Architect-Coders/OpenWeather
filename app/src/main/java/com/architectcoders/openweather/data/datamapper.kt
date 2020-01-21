package com.architectcoders.openweather.data

import com.architectcoders.domain.Weather
import com.architectcoders.openweather.data.database.Weather as RoomWeather
import com.architectcoders.openweather.data.server.WeatherResult as ServerWeather

fun Weather.toRoomWeather(): RoomWeather =
    RoomWeather(
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

fun RoomWeather.toDomainWeather(): Weather = Weather(
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

fun ServerWeather.toDomainWeather(): Weather = Weather(
    0,
    name,
    weather[0].main,
    weather[0].description,
    main.temp.toString(),
    main.pressure.toString(),
    main.humidity.toString(),
    main.tempMin.toString(),
    main.tempMax.toString(),
    (System.currentTimeMillis() / 1000).toString()
)