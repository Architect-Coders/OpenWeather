package com.architectcoders.openweather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.architectcoders.domain.Weather
import com.architectcoders.openweather.ui.common.Event
import com.architectcoders.openweather.ui.common.Scope
import com.architectcoders.usescases.GetWeather
import kotlinx.coroutines.launch

class MainViewModel(var getWeather: GetWeather)
    : ViewModel(), Scope by Scope.Impl() {

    private val _model = MutableLiveData<UiModel>()
    val model: LiveData<UiModel>
        get() {
            if (_model.value == null) refresh()
            return _model
        }

    private val _navigation = MutableLiveData<Event<Weather>>()
    val navigation: LiveData<Event<Weather>> = _navigation

    sealed class UiModel {

        object ShowTurnOnPermission : UiModel()
        object ShowTurnOnLocation : UiModel()
        object Loading : UiModel()
        class Content(val weather: Weather) : UiModel()
        class CheckLocation :UiModel()
        object RequestLocationPermission : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun checkLocation() {
        _model.value = UiModel.CheckLocation()
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getWeather.invoke())
        }
    }

    fun onWeatherClicked(weather: Weather) {
        _navigation.value = Event(weather)
    }

    fun showTurnOnPermission(){
        _model.value = UiModel.ShowTurnOnPermission
    }

    fun showTurnOnLocation(){
        _model.value = UiModel.ShowTurnOnLocation
    }

}