package com.architectcoders.openweather

import android.app.Application
import androidx.room.Room
import com.architectcoders.openweather.data.database.WeatherDatabase
import com.architectcoders.openweather.di.DaggerWeatherComponent
import com.architectcoders.openweather.di.WeatherComponent

class WeatherApp : Application() {

    lateinit var component: WeatherComponent
        private set

    override fun onCreate() {
        super.onCreate()

        component= DaggerWeatherComponent
            .factory()
            .create(this)
    }
}