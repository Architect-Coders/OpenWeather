package com.architectcoders.usescases

import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.domain.Weather

class FindByTimestamp(private val weatherRepository: WeatherRepository) {

    suspend fun invoke(timestamp: String):Weather = weatherRepository.findByTimestamp(timestamp)
}