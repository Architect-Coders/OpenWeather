package com.architectcoders.openweather

import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.openweather.testshared.mockedWeather
import com.architectcoders.usescases.FindByTimestamp
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FindByTimestampTest {


    val timestamp = "123456"

    @Mock
    lateinit var repository: WeatherRepository

    lateinit var findByTimestampTest: FindByTimestamp

    @Before
    fun setUp(){
        findByTimestampTest = FindByTimestamp(repository)
    }

    @Test
    fun `invoke call weather repository`(){
        runBlocking {
            whenever(repository.findByTimestamp(timestamp)).thenReturn(mockedWeather)

            val result = findByTimestampTest.invoke(timestamp)

            Assert.assertEquals(mockedWeather, result)
        }
    }
}