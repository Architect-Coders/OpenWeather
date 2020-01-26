package com.architectcoders.openweather

import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.domain.Weather
import com.architectcoders.usescases.GetWeather
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetWeatherTest {

    @Mock
    lateinit var repository: WeatherRepository

    lateinit var getWeather: GetWeather


    @Before
    fun setUp() {
        getWeather = GetWeather(repository)
    }

    @Test
    fun `invoke calls weather`(){
        runBlocking {

            whenever(repository.getWeather()).thenReturn(mockedWeather)

            val result = getWeather.invoke()

            Assert.assertEquals(mockedWeather, result)
        }
    }
}