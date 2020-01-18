package com.architectcoders.usescases

import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.domain.Weather

class FindByCity(private val weatherRepository: WeatherRepository) {

    suspend fun invoke(city: String): List<Weather> = weatherRepository.findByCity(city)
}