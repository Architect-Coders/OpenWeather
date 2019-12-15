package com.architectcoders.openweather.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architectcoders.openweather.model.WeatherRepository
import com.architectcoders.openweather.model.database.Weather
import com.architectcoders.openweather.ui.common.ScopedViewModel
import kotlinx.coroutines.launch

class DetailViewModel(
    private val timestamp: String,
    private var weatherRepository: WeatherRepository
) : ScopedViewModel() {

    sealed class UiModel {
        class ShowWeatherByCity(val weatherList: List<Weather>): UiModel()
        class Content(val weather: Weather) : UiModel()
    }

    private val _model = MutableLiveData<UiModel>()

    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) findWeather()
            return _model
        }

    private fun findWeather() = launch {
        _model.value = UiModel.Content(weatherRepository.findByTimestamp(timestamp))
    }

    fun findWeatherByCity(city: String) = launch {
        _model.value = UiModel.ShowWeatherByCity(weatherRepository.findByCity(city))
    }

}