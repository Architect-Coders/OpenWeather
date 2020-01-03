package com.architectcoders.openweather.ui.main

import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.architectcoders.domain.Weather
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

    sealed class UiModel {

        object ShowTurnOnPermission : UiModel()
        object ShowTurnOnLocation : UiModel()
        object Loading : UiModel()
        class Content(val weather: Weather) : UiModel()
        class Navigation(val weather: Weather) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    init {
        initScope()
    }

    private fun refresh() {
        _model.value = UiModel.RequestLocationPermission
    }

    fun onCoarsePermissionRequested() {
        launch {
            _model.value = UiModel.Loading
            _model.value = UiModel.Content(getWeather.invoke())
        }
    }

    fun checkLocation(locationManager: LocationManager) {

        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            networkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }
        if (!gpsEnabled && !networkEnabled) {
            _model.value = UiModel.ShowTurnOnLocation
        } else {
            refresh()
        }
    }

    fun onWeatherClicked(weather: Weather) {
        _model.value = UiModel.Navigation(weather)
    }

    fun showTurnOnPermission(){
        _model.value = UiModel.ShowTurnOnPermission
    }

}