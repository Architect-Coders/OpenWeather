package com.architectcoders.openweather.di

import android.app.Application
import androidx.room.Room
import com.architectcoders.data.repository.PermissionChecker
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.LocationDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.openweather.R
import com.architectcoders.openweather.data.AndroidPermissionChecker
import com.architectcoders.openweather.data.PlayServicesLocationDataSource
import com.architectcoders.openweather.data.database.RoomDataSource
import com.architectcoders.openweather.data.database.WeatherDatabase
import com.architectcoders.openweather.data.server.WeatherDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule  {

    @Provides
    @Singleton
    @Named("apiKey")
    fun apiKeyProvider(app: Application): String = app.getString(R.string.key_app)

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
    app,
    WeatherDatabase::class.java, "weather-db"
    ).build()


    @Provides
    fun localDataSourceProvider(db: WeatherDatabase): LocalDataSource = RoomDataSource(db)

    @Provides
    fun remoteDataSourceProvider(): RemoteDataSource = WeatherDataSource()

    @Provides
    fun locationDataSourceProvider(app: Application): LocationDataSource =
        PlayServicesLocationDataSource(app)

    @Provides
    fun permissionCheckerProvider(app: Application): PermissionChecker =
        AndroidPermissionChecker(app)
}