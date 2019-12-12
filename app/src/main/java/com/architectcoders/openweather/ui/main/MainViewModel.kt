package com.architectcoders.openweather.ui.main

import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.architectcoders.openweather.model.WeatherRepository
import com.architectcoders.openweather.model.WeatherResult
import com.architectcoders.openweather.model.detail.Detail
import com.architectcoders.openweather.ui.commun.Scope
import kotlinx.coroutines.launch

class MainViewModel(var weatherRepository: WeatherRepository)
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
        class Content(val weatherResult: WeatherResult) : UiModel()
        class Navigation(val detail: Detail) : UiModel()
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
            _model.value = UiModel.Content(weatherRepository.findWeather())
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

    fun onWeatherClicked(detail: Detail) {
        _model.value = UiModel.Navigation(detail)
    }

    fun showTurnOnPermission(){
        _model.value = UiModel.ShowTurnOnPermission
    }

    @Suppress("UNCHECKED_CAST")
    class MainViewModelFactory(val weatherRepository: WeatherRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainViewModel(weatherRepository) as T
    }

}