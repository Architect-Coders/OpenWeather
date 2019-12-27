package com.architectcoders.openweather.ui.main

import android.location.LocationManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.architectcoders.openweather.model.WeatherRepository
import com.architectcoders.openweather.model.database.Weather
import com.architectcoders.openweather.ui.common.Scope
import com.architectcoders.openweather.ui.common.Event
import kotlinx.coroutines.launch

class MainViewModel(var weatherRepository: WeatherRepository)
    : ViewModel(), Scope by Scope.Impl() {

    private val _showLocationOn = MutableLiveData<String>()
    val showLocationOn: LiveData<String> get() = _showLocationOn

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> get() = _loading

    private val _weather = MutableLiveData<String>()
    val weather: LiveData<String> get() = _weather

    private val _navigateToWeather = MutableLiveData<Event<Int>>()
    val navigateToWeather: LiveData<Event<Int>> get() = _navigateToWeather

    private val _requestLocationPermission = MutableLiveData<Event<Unit>>()
    val requestLocationPermission: LiveData<Event<Unit>> get() = _requestLocationPermission


    sealed class UiModel {

        object ShowTurnOnPermission : UiModel()
        object ShowTurnOnLocation : UiModel()
        object Loading : UiModel()
        class Content(val weather: String) : UiModel()
        class Navigation(val weather: Weather) : UiModel()
        object RequestLocationPermission : UiModel()
    }

    init {
        initScope()
        refresh()
    }

    private fun refresh() {
        _requestLocationPermission.value = Event(Unit)
    }

    fun onCoarsePermissionRequested() {
        launch {
            _loading.value = true
            _weather.value = weatherRepository.findWeather().city
            _loading.value = false
        }
    }

    fun checkLocation(locationManager: LocationManager, message :String) {

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
            _showLocationOn.value = message
        } else {
            refresh()
        }
    }

    fun onWeatherClicked(weather: Weather) {
        _navigateToWeather.value = Event(weather.id)
    }

    fun showTurnOnPermission(message :String){
        _showLocationOn.value = message
    }

    @Suppress("UNCHECKED_CAST")
    class MainViewModelFactory(val weatherRepository: WeatherRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T =
            MainViewModel(weatherRepository) as T
    }

}