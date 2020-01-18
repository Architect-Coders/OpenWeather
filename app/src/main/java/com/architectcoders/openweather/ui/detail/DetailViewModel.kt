package com.architectcoders.openweather.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.architectcoders.domain.Weather
import com.architectcoders.openweather.ui.common.ScopedViewModel
import com.architectcoders.usescases.FindByCity
import com.architectcoders.usescases.FindByTimestamp
import kotlinx.coroutines.launch

class DetailViewModel(
    private val timestamp: String,
    private var findByCity: FindByCity,
    private var findByTimestamp: FindByTimestamp
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
        _model.value = UiModel.Content(findByTimestamp.invoke(timestamp))
    }

    fun findWeatherByCity(city: String) = launch {
        _model.value = UiModel.ShowWeatherByCity(findByCity.invoke(city))
    }

}