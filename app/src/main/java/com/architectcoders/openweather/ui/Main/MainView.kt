package com.architectcoders.openweather.ui.Main

import com.architectcoders.openweather.model.WeatherResult

interface MainView {

    fun updateData(resultWeather: WeatherResult)

    fun showTurnOnLocation()

    fun showProgressBar()

    fun hideProgressBar()
}