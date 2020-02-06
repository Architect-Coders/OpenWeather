package com.architectcoders.openweather

import android.app.Application
import com.architectcoders.data.repository.PermissionChecker
import com.architectcoders.data.repository.RegionRepository
import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.LocationDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.openweather.data.AndroidPermissionChecker
import com.architectcoders.openweather.data.PlayServicesLocationDataSource
import com.architectcoders.openweather.data.database.RoomDataSource
import com.architectcoders.openweather.data.database.WeatherDatabase
import com.architectcoders.openweather.data.server.WeatherDB
import com.architectcoders.openweather.data.server.WeatherDataSource
import com.architectcoders.openweather.ui.detail.DetailActivity
import com.architectcoders.openweather.ui.detail.DetailViewModel
import com.architectcoders.openweather.ui.main.MainActivity
import com.architectcoders.openweather.ui.main.MainViewModel
import com.architectcoders.usescases.FindByCity
import com.architectcoders.usescases.FindByTimestamp
import com.architectcoders.usescases.GetWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.qualifier.named
import org.koin.dsl.module


fun Application.initDI() {
    startKoin {
        androidLogger()
        androidContext(this@initDI)
        modules(listOf(appModule, dataModule, scopesModule))
    }
}

private val appModule = module {
    single(named("apiKey")) { androidApplication().getString(R.string.key_app) }
    single { WeatherDatabase.build(get()) }
    factory<LocalDataSource> { RoomDataSource(get()) }
    factory<RemoteDataSource> { WeatherDataSource(get()) }
    factory<LocationDataSource> { PlayServicesLocationDataSource(get()) }
    factory<PermissionChecker> { AndroidPermissionChecker(get()) }
    single<CoroutineDispatcher> { Dispatchers.Main }
    single(named("baseUrl")) { "https://api.openweathermap.org/data/2.5/" }
    single { WeatherDB(get(named("baseUrl"))) }
}

val dataModule = module {
    factory { RegionRepository(get(), get()) }
    factory { WeatherRepository(get(), get(), get(), get(named("apiKey"))) }
}

private val scopesModule = module {
    scope(named<MainActivity>()) {
        viewModel { MainViewModel(get(), get()) }
        scoped { GetWeather(get()) }
    }

    scope(named<DetailActivity>()) {
        viewModel { (timestamp: String) -> DetailViewModel(timestamp, get(), get(), get()) }
        scoped { FindByCity(get()) }
        scoped { FindByTimestamp(get()) }
    }
}