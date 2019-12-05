package com.architectcoders.openweather.ui.main

import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.architectcoders.openweather.R
import com.architectcoders.openweather.model.WeatherRepository
import com.architectcoders.openweather.model.WeatherResult
import com.architectcoders.openweather.model.detail.Detail
import com.architectcoders.openweather.ui.detail.DetailActivity
import com.architectcoders.openweather.ui.commun.getImageFromString
import com.architectcoders.openweather.ui.commun.startActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel
    //private val adapter = CitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)




        ViewModelProviders.of(
            this,
            MainViewModel.MainViewModelFactory(WeatherRepository(this))
        )[MainViewModel::class.java]

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
            is MainViewModel.UiModel.Content -> updateData(model.weatherResult)
            is MainViewModel.UiModel.ShowTurnOnLocation -> showTurnOnLocation()
            is MainViewModel.UiModel.Navigation -> startActivity<DetailActivity> {
                putExtra(
                    DetailActivity.WEATHER, model.detail
                )
            }
        }
    }


    private fun updateData(resultWeather: WeatherResult) {

        val weatherList = resultWeather.weather


        location_city.text = resultWeather.name
        location_weather_imageView.setImageDrawable(
            getImageFromString(
                weatherList[0].main,
                this
            )
        )

        location_city.setOnClickListener {
            viewModel::onWeatherClicked
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

}
