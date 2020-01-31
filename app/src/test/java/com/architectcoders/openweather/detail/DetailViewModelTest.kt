package com.architectcoders.openweather.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.architectcoders.openweather.testshared.mockedWeather
import com.architectcoders.openweather.ui.detail.DetailViewModel
import com.architectcoders.usescases.FindByCity
import com.architectcoders.usescases.FindByTimestamp
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class DetailViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var findByCity: FindByCity

    @Mock
    lateinit var findByTimestamp: FindByTimestamp

    @Mock
    lateinit var observer: Observer<DetailViewModel.UiModel>

    lateinit var detailViewModel: DetailViewModel

    private val timestamp = "123456"
    private val city = "Madrid"

    @Before
    fun setup() {
        detailViewModel =
            DetailViewModel(timestamp, findByCity, findByTimestamp, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData finds the weather by timestamp`() {
        runBlocking {
            whenever(findByTimestamp.invoke(timestamp)).thenReturn(mockedWeather)

            detailViewModel.model.observeForever(observer)

            verify(observer).onChanged(DetailViewModel.UiModel.Content(mockedWeather))
        }
    }

    @Test
    fun `observing LiveData finds the weather by city`() {
        runBlocking {
            val weatherList = listOf(mockedWeather.copy(id = 1))
            whenever(findByTimestamp.invoke(timestamp)).thenReturn(mockedWeather)
            whenever(findByCity.invoke(city)).thenReturn(weatherList)
            detailViewModel.model.observeForever(observer)

            detailViewModel.findWeatherByCity(city)

            verify(observer).onChanged(DetailViewModel.UiModel.ShowWeatherByCity(weatherList))
        }
    }
}