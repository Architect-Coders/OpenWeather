package com.architectcoders.openweather.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.architectcoders.data.repository.RegionRepository
import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.domain.Weather
import com.architectcoders.openweather.R
import com.architectcoders.openweather.model.AndroidPermissionChecker
import com.architectcoders.openweather.model.PlayServicesLocationDataSource
import com.architectcoders.openweather.model.database.RoomDataSource
import com.architectcoders.openweather.model.server.WeatherDataSource
import com.architectcoders.openweather.ui.common.app
import com.architectcoders.openweather.ui.common.getImageFromString
import com.architectcoders.openweather.ui.common.getViewModel
import com.architectcoders.usescases.FindByCity
import com.architectcoders.usescases.FindByTimestamp
import kotlinx.android.synthetic.main.detail_activity.*

class DetailActivity : AppCompatActivity() {

    companion object {
        const val WEATHER = "DetailActivity:weather"
    }

    private lateinit var viewModel: DetailViewModel
    private lateinit var adapter: DetailAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.detail_activity)

        viewModel = getViewModel {
            DetailViewModel(
                intent.getStringExtra(WEATHER),
                FindByCity(
                    WeatherRepository(
                        RoomDataSource(app.db),
                        WeatherDataSource(),
                        RegionRepository(
                            PlayServicesLocationDataSource(app),
                            AndroidPermissionChecker(app)
                        ),
                        resources.getString(R.string.key_app)
                    )
                ),
                FindByTimestamp(
                    WeatherRepository(
                        RoomDataSource(app.db),
                        WeatherDataSource(),
                        RegionRepository(
                            PlayServicesLocationDataSource(app),
                            AndroidPermissionChecker(app)
                        ),
                        resources.getString(R.string.key_app)
                    ))
            )
        }
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
