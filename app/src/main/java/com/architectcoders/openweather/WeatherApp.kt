package com.architectcoders.openweather

import android.app.Application
import androidx.room.Room
import com.architectcoders.openweather.model.database.WeatherDatabase

class WeatherApp : Application() {

    lateinit var db: WeatherDatabase
        private set

    override fun onCreate() {
        super.onCreate()

        db = Room.databaseBuilder(
            this,
            WeatherDatabase::class.java, "weather-db"
        ).build()
    }
}