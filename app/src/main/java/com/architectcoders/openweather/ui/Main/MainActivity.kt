package com.architectcoders.openweather.ui.Main

import android.content.Context
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import com.architectcoders.openweather.R
import com.architectcoders.openweather.model.WeatherRepository
import com.architectcoders.openweather.model.WeatherResult
import com.architectcoders.openweather.model.detail.Detail
import com.architectcoders.openweather.ui.detail.DetailActivity
import com.architectcoders.openweather.ui.commun.CoroutineScopeActivity
import com.architectcoders.openweather.ui.commun.getImageFromString
import com.architectcoders.openweather.ui.commun.startActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch

class MainActivity : CoroutineScopeActivity(), MainView {

    private val presenter by lazy { MainPresenter(WeatherRepository(this)) }
    //private val adapter = CitiesAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recycler.adapter = adapter
        presenter.onCreate(this)
        location.setOnClickListener {
            presenter.checkLocation(getSystemService(Context.LOCATION_SERVICE) as LocationManager)
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    override fun updateData(resultWeather: WeatherResult) {

        val weatherList = resultWeather.weather


        location_city.text = resultWeather.name
        location_weather_imageView.setImageDrawable(
            getImageFromString(
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


    override fun showTurnOnLocation() {
        val snackbar = Snackbar.make(
            main_constraintLayout, getString(R.string.location_turn_on),
            Snackbar.LENGTH_LONG
        ).setAction("Action", null)
        snackbar.setActionTextColor(Color.BLUE)
        snackbar.show()
    }

    override fun showProgressBar() {
        locationProgressBar.visibility = VISIBLE
    }

    override fun hideProgressBar() {
        locationProgressBar.visibility = GONE
    }
}
