package com.architectcoders.openweather

import android.os.Bundle
import com.architectcoders.openweather.model.WeatherRepository
import com.architectcoders.openweather.ui.CitiesAdapter
import com.architectcoders.openweather.ui.commun.CoroutineScopeActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.launch


class MainActivity : CoroutineScopeActivity() {



    private val weatherRepository: WeatherRepository by lazy { WeatherRepository(this) }
    private val adapter = CitiesAdapter()

        override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler.adapter = adapter

        launch {
            adapter.cities = weatherRepository.findWeather().weather
        }
    }
}
