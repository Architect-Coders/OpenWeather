package com.architectcoders.openweather.ui.detail

import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.usescases.FindByCity
import com.architectcoders.usescases.FindByTimestamp
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

@Module
class DetailActivityModule(private val timestamp: String){
    @Provides
    fun detailViewModelProvider(
        findByCity: FindByCity,
        findByTimestamp: FindByTimestamp
    ): DetailViewModel {
        return DetailViewModel(timestamp, findByCity, findByTimestamp)
    }

    @Provides
    fun findByCityProvider(weatherRepository: WeatherRepository) = FindByCity(weatherRepository)

    @Provides
    fun findByTimestamp(weatherRepository: WeatherRepository) = FindByTimestamp(weatherRepository)
}
@Subcomponent(modules = [(DetailActivityModule::class)])
interface DetailActivityComponent {
    val detailViewModel: DetailViewModel
}