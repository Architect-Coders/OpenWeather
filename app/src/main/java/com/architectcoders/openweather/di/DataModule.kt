package com.architectcoders.openweather.di

import com.architectcoders.data.repository.PermissionChecker
import com.architectcoders.data.repository.RegionRepository
import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.LocationDataSource
import com.architectcoders.data.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import javax.inject.Named

@Module
class DataModule {

    @Provides
    fun regionRepositoryProvider(
        locationDataSource: LocationDataSource,
        permissionChecker: PermissionChecker
    ) = RegionRepository(locationDataSource, permissionChecker)

    @Provides
    fun weatherRepositoryProvider(
        localDataSource: LocalDataSource,
        remoteDataSource: RemoteDataSource,
        regionRepository: RegionRepository,
        @Named("apiKey") apiKey: String
    ) = WeatherRepository(localDataSource, remoteDataSource, regionRepository, apiKey)

}