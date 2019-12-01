package com.architectcoders.openweather

import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import com.architectcoders.openweather.model.WeatherRepository
import com.architectcoders.openweather.model.WeatherResult
import com.architectcoders.openweather.model.detail.Detail
import com.architectcoders.openweather.model.image.CreateImage
import com.architectcoders.openweather.ui.CitiesAdapter
import com.architectcoders.openweather.ui.DetailActivity
import com.architectcoders.openweather.ui.commun.CoroutineScopeActivity
import com.architectcoders.openweather.ui.commun.startActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch


class MainActivity : CoroutineScopeActivity() {


    private val weatherRepository: WeatherRepository by lazy { WeatherRepository(this) }
    //private val adapter = CitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recycler.adapter = adapter

        location.setOnClickListener {
            checkLocation()
        }
    }

    private fun checkLocation() {
        val lm = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gpsEnabled = false
        var networkEnabled = false

        try {
            gpsEnabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        } catch (ex: Exception) {
        }

        try {
            networkEnabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        } catch (ex: Exception) {
        }
        if (!gpsEnabled && !networkEnabled) {
            onSNACK(getString(R.string.location_turn_on))
        } else {
            getWeatherWithLocation()
        }
    }

    private fun getWeatherWithLocation() {
        launch {
            showData(weatherRepository.findWeather())
        }
    }

    private fun showData(resultWeather: WeatherResult) {
        val weatherList = resultWeather.weather
        val createImage = CreateImage()
        location_city.text = resultWeather.name
        location_weather_imageView.setImageDrawable(
            createImage.getImageFromString(
                weatherList[0].main,
                this
            )
        )
        location_city.setOnClickListener {
            startActivity<DetailActivity> {
                putExtra(
                    DetailActivity.WEATHER, Detail(
                        resultWeather.name,
                        weatherList[0].main,
                        weatherList[0].description,
                        "${resultWeather.main.temp}",
                        "${resultWeather.main.pressure}",
                        "${resultWeather.main.humidity}",
                        "${resultWeather.main.tempMin}",
                        "${resultWeather.main.tempMax}"
                    )
                )
            }
        }
    }

    private fun onSNACK(message: String) {
        val snackbar = Snackbar.make(
            main_constraintLayout, message,
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        snackbar.show()
    }
}
