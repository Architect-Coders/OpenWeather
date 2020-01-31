package com.architectcoders.openweather.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.architectcoders.domain.Weather
import com.architectcoders.openweather.ui.common.Event
import com.architectcoders.openweather.ui.common.Scope
import com.architectcoders.openweather.ui.common.ScopedViewModel
import com.architectcoders.usescases.GetWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.launch

class MainViewModel(
    var getWeather: GetWeather,
    uiDispatcher: CoroutineDispatcher
) : ScopedViewModel(uiDispatcher) {

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
        object ShowCanCheckYourInternet : UiModel()
        object Loading : UiModel()
        data class Content(val weather: Weather) : UiModel()
        object RequestLocationPermission : UiModel()
        object RequestCheckLocation : UiModel()
        object RequestCheckInternet : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    private fun myLaunch() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getWeather.invoke())
        }
    }

    fun onCoarsePermissionRequested(continuation: Boolean) {
        if (continuation) {
            _model.value = UiModel.RequestCheckLocation
        } else {
            _model.value = UiModel.ShowTurnOnPermission
        }
    }

    fun checkLocation(continuation: Boolean) {
        if (continuation) {
            _model.value = UiModel.RequestCheckInternet
        } else {
            _model.value = UiModel.ShowTurnOnLocation
        }
    }

    fun checkInternet(continuation: Boolean) {
        if (continuation) {
            myLaunch()
        } else {
            _model.value = UiModel.ShowCanCheckYourInternet
        }
    }

    fun onWeatherClicked(weather: Weather) {
        _navigation.value = Event(weather)
    }

}