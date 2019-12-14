package com.architectcoders.openweather.model.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Weather (
    @PrimaryKey(autoGenerate = true) val id: Int,
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