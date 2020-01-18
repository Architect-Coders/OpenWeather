package com.architectcoders.openweather.ui.main

import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.usescases.GetWeather
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class MainActivityModule {

    @Provides
    fun mainViewModelProvider(getWeather: GetWeather) = MainViewModel(getWeather)

    @Provides
    fun getWeatherProvider(weatherRepository: WeatherRepository) =
        GetWeather(weatherRepository)
}

@Subcomponent(modules = [(MainActivityModule::class)])
interface MainActivityComponent {
    val mainViewModel: MainViewModel
}