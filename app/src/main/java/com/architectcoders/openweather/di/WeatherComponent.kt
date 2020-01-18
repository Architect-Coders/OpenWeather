package com.architectcoders.openweather.di

import android.app.Application
import com.architectcoders.openweather.ui.detail.DetailActivityComponent
import com.architectcoders.openweather.ui.detail.DetailActivityModule
import com.architectcoders.openweather.ui.main.MainActivityComponent
import com.architectcoders.openweather.ui.main.MainActivityModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class, DataModule::class])
interface WeatherComponent {

    fun plus(module: MainActivityModule): MainActivityComponent
    fun plus(module: DetailActivityModule) : DetailActivityComponent

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): WeatherComponent
    }
}