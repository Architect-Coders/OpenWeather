package com.architectcoders.openweather.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.openweather.FakeLocalDataSource
import com.architectcoders.openweather.defaultFakeWeather
import com.architectcoders.openweather.initMockedDi
import com.architectcoders.openweather.testshared.mockedWeather
import com.architectcoders.openweather.ui.detail.DetailViewModel
import com.architectcoders.openweather.ui.main.MainViewModel
import com.architectcoders.usescases.FindByCity
import com.architectcoders.usescases.FindByTimestamp
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.parameter.parametersOf
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailIntegrationTests: AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    private lateinit var vm: DetailViewModel
    private lateinit var localDataSource: FakeLocalDataSource
    val city ="Madrid"

    @Before
    fun setUp() {
        val vmModule = module {
            factory { (timestamp: String) -> DetailViewModel(timestamp, get(), get(), get()) }
            factory { FindByTimestamp(get()) }
            factory { FindByCity(get()) }
        }

        initMockedDi(vmModule)
        vm = get { parametersOf("123456") }

        localDataSource = get<LocalDataSource>() as FakeLocalDataSource
        localDataSource.listWeather = defaultFakeWeather
    }


    @Test
    fun `observing LiveData finds the weather`() {
        vm.model.observeForever(observer)

        verify(observer).onChanged(DetailViewModel.UiModel.Content(mockedWeather))
    }

    @Test
    fun `get list of weather by city`() {
        vm.model.observeForever(observer)

        vm.findWeatherByCity(city)

        verify(observer).onChanged(DetailViewModel.UiModel.ShowWeatherByCity(defaultFakeWeather))
    }
}