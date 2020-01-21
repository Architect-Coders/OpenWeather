package com.architectcoders.openweather.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.architectcoders.domain.Weather
import com.architectcoders.openweather.R
import com.architectcoders.openweather.ui.common.app
import com.architectcoders.openweather.ui.common.getImageFromString
import com.architectcoders.openweather.ui.common.getViewModel
import kotlinx.android.synthetic.main.detail_activity.*
import org.koin.android.scope.currentScope
import org.koin.android.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailActivity : AppCompatActivity() {

    companion object {
        const val WEATHER = "DetailActivity:weather"
    }
    private val viewModel: DetailViewModel by currentScope.viewModel(this) {
        parametersOf(intent.getStringExtra(WEATHER))
    }

    private lateinit var adapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        viewModel.model.observe(this, Observer(::updateUI))
        initWeatherList()
    }

    private fun updateUI(model: DetailViewModel.UiModel) {

        when (model) {
            is DetailViewModel.UiModel.Content -> updateData(model.weather)
            is DetailViewModel.UiModel.ShowWeatherByCity -> adapter.weatherList = model.weatherList
        }
    }

    private fun updateData(weather: Weather) {

        viewModel.findWeatherByCity(weather.city)

        weatherDetailToolbar.title = weather.city
        showImages(weather.main)

        weatherDetailSummary.text = weather.main
        weatherDetailInfo.setWeather(weather)
    }

    private fun showImages(image: String) {
        weatherDetailImage.setImageDrawable(
            getImageFromString(
                image,
                this
            )
        )
        weatherImageView.setImageDrawable(
            getImageFromString(
                image,
                this
            )
        )
    }

    private fun initWeatherList() {
        weatherRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        adapter = DetailAdapter(this)
        weatherRecyclerView.adapter = adapter
    }
}
