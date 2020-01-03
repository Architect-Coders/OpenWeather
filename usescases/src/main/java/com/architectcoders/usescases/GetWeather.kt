package com.architectcoders.usescases

import com.architectcoders.data.WeatherRepository
import com.architectcoders.domain.Weather

class GetWeather(private val weatherRepository: WeatherRepository) {

    suspend fun invoke():Weather = weatherRepository.getWeather()
}