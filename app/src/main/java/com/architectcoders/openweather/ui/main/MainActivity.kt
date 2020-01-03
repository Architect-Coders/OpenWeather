package com.architectcoders.openweather.ui.main

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.architectcoders.data.repository.RegionRepository
import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.domain.Weather
import com.architectcoders.openweather.PermissionRequester
import com.architectcoders.openweather.R
import com.architectcoders.openweather.model.AndroidPermissionChecker
import com.architectcoders.openweather.model.PlayServicesLocationDataSource
import com.architectcoders.openweather.model.database.RoomDataSource
import com.architectcoders.openweather.model.server.WeatherDataSource
import com.architectcoders.openweather.ui.common.app
import com.architectcoders.openweather.ui.detail.DetailActivity
import com.architectcoders.openweather.ui.common.getImageFromString
import com.architectcoders.openweather.ui.common.getViewModel
import com.architectcoders.openweather.ui.common.startActivity
import com.architectcoders.usescases.GetWeather
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    private val coarsePermissionRequester = PermissionRequester(
        this,
        Manifest.permission.ACCESS_COARSE_LOCATION
    )

    //private val adapter = CitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = getViewModel {
            val localDataSource = RoomDataSource(app.db)
            MainViewModel(GetWeather(
                WeatherRepository(
                    localDataSource,
                    WeatherDataSource(),
                    RegionRepository(
                        PlayServicesLocationDataSource(app),
                        AndroidPermissionChecker(app)
                    ),
                    resources.getString(R.string.key_app)
                )
            ))
        }

        //recycler.adapter = adapter

        location.setOnClickListener {
            viewModel.checkLocation(getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        }
        viewModel.model.observe(this, Observer(::updateUi))
    }

    private fun updateUi(model: MainViewModel.UiModel) {

        locationProgressBar.visibility =
            if (model is MainViewModel.UiModel.Loading) VISIBLE else GONE


        when (model) {
            is MainViewModel.UiModel.Content -> updateData(model.weather)
            is MainViewModel.UiModel.ShowTurnOnLocation -> showTurnOnLocation()
            is MainViewModel.UiModel.ShowTurnOnPermission -> showTurnOnPermission()
            is MainViewModel.UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(
                    DetailActivity.WEATHER, model.weather.timestamp
                )
            }
            MainViewModel.UiModel.RequestLocationPermission -> coarsePermissionRequester.request {
                if (it) {
                    viewModel.onCoarsePermissionRequested()
                } else {
                    viewModel.showTurnOnPermission()
                }
            }
        }
    }

    private fun updateData(weather: Weather) {

        location_city.text = weather.city
        location_weather_imageView.setImageDrawable(
            getImageFromString(
                weather.main,
                this
            )
        )

        location_city.setOnClickListener {

            viewModel.onWeatherClicked(weather)
        }
    }

    private fun showTurnOnLocation() {
        val snackbar = Snackbar.make(
            main_constraintLayout, getString(R.string.location_turn_on),
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        snackbar.show()
    }

    private fun showTurnOnPermission() {
        val snackbar = Snackbar.make(
            main_constraintLayout, getString(R.string.location_turn_on_permission),
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        snackbar.show()
    }

}
