package com.architectcoders.openweather

import com.architectcoders.data.repository.RegionRepository
import com.architectcoders.data.repository.WeatherRepository
import com.architectcoders.data.source.LocalDataSource
import com.architectcoders.data.source.RemoteDataSource
import com.architectcoders.openweather.testshared.mockedWeather
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class WeatherRepositoryTest {

    @Mock
    lateinit var localDataSource: LocalDataSource

    @Mock
    lateinit var remoteDataSource: RemoteDataSource

    @Mock
    lateinit var regionRepository: RegionRepository

    lateinit var weatherRepository: WeatherRepository

    private val apiKey = "1a2b3c4d"

    @Before
    fun setUp() {
        weatherRepository =
            WeatherRepository(localDataSource, remoteDataSource, regionRepository, apiKey)
    }


    @Test
    fun `findByTimestamp gets from local data source`(){
        runBlocking {
            whenever(localDataSource.findByTimestamp("123456")).thenReturn(mockedWeather)
            val result = weatherRepository.findByTimestamp("123456")

            Assert.assertEquals(mockedWeather, result)
        }
    }

    @Test
    fun `findBy gets from local data source`() {
        runBlocking {
            val localWeather = listOf(mockedWeather.copy(1))
            whenever(localDataSource.findByCity("Madrid")).thenReturn(localWeather)

            val result = weatherRepository.findByCity("Madrid")

            Assert.assertEquals(result, localWeather)
        }
    }

    /*@Test
    fun `getWeather gets from remote data source`() {
        runBlocking {

            whenever(remoteDataSource.getWeather("1", "1", apiKey)).thenReturn(mockedWeather)
            whenever(localDataSource.saveWeather(mockedWeather))
            val result = weatherRepository.getWeather()

            Assert.assertEquals(mockedWeather, result)
        }
    }*/
}
