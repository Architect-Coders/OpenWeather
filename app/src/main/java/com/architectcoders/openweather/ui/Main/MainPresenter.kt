package com.architectcoders.openweather.ui.Main

import android.location.LocationManager
import com.architectcoders.openweather.model.WeatherRepository
import com.architectcoders.openweather.ui.commun.Scope
import kotlinx.coroutines.launch

class MainPresenter(var weatherRepository: WeatherRepository) : Scope by Scope.Impl() {


    private var view: MainView? = null

    fun onCreate(view: MainView) {
        initScope()
        this.view = view
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
            view?.showTurnOnLocation()
        } else {
            getWeatherWithLocation()
        }
    }


    private fun getWeatherWithLocation() {
        launch {
            view?.showProgressBar()
            view?.updateData(weatherRepository.findWeather())
            view?.hideProgressBar()
        }
    }

    fun onDestroy() {
        this.view = null
        destroyScope()
    }

}