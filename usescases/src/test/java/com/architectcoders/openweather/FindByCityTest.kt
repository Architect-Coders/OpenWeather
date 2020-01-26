package com.architectcoders.openweather

import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.domain.Weather
import com.architectcoders.usescases.FindByCity
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FindByCityTest {

    val city = "Madrid"

    @Mock
    lateinit var repository: WeatherRepository

    lateinit var findByCity: FindByCity

    @Before
    fun setUp(){
        findByCity = FindByCity(repository)
    }

    @Test
    fun `invoke call weather repository`(){
        runBlocking {

            val weathers = listOf(mockedWeather.copy(id=1))

            whenever(repository.findByCity(city)).thenReturn(weathers)

            val result = findByCity.invoke(city)

            Assert.assertEquals(weathers, result)
        }

    }
}