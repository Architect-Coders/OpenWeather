package com.architectcoders.openweather.ui.main

import android.Manifest
import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.architectcoders.openweather.PermissionRequester
import com.architectcoders.openweather.R
import com.architectcoders.openweather.databinding.ActivityMainBinding
import com.architectcoders.openweather.model.WeatherRepository
import com.architectcoders.openweather.model.database.Weather
import com.architectcoders.openweather.ui.common.*
import com.architectcoders.openweather.ui.detail.DetailActivity
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

        viewModel = getViewModel { MainViewModel(WeatherRepository(app)) }

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.viewmodel = viewModel
        binding.lifecycleOwner = this

        //recycler.adapter = adapter
        viewModel.navigateToWeather.observe(this, EventObserver { id ->
            startActivity<DetailActivity> {
                putExtra(DetailActivity.WEATHER, id)
            }
        })

        viewModel.requestLocationPermission.observe(this, EventObserver {
            coarsePermissionRequester.request {
                viewModel.onCoarsePermissionRequested()
            }
        })
        location.setOnClickListener {
            viewModel.checkLocation(getSystemService(Context.LOCATION_SERVICE) as LocationManager,
                resources.getString(R.string.location_turn_on_permission))
        }
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
                    viewModel.showTurnOnPermission(resources.getString(R.string.location_turn_on_permission))
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
