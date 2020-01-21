package com.architectcoders.openweather

import android.app.Application

class WeatherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        initDI()
    }
}