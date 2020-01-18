package com.architectcoders.domain

data class Weather(
    val id: Int,
    val city: String,
    val main: String,
    val description: String,
    val temperature: String,
    val pressure: String,
    val humidity: String,
    val temp_min: String,
    val temp_max: String,
    val timestamp: String
)