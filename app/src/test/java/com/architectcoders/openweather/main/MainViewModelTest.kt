package com.architectcoders.openweather.main


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.architectcoders.openweather.testshared.mockedWeather
import com.architectcoders.openweather.ui.main.MainViewModel
import com.architectcoders.usescases.GetWeather
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
class MainViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var getWeather: GetWeather

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

    lateinit var mainViewModel: MainViewModel

    @Before
    fun setUp(){
        mainViewModel = MainViewModel(getWeather, Dispatchers.Unconfined)
    }

    @Test
    fun `observing LiveData launches location permission request`() {

        mainViewModel.model.observeForever(observer)

        verify(observer).onChanged(MainViewModel.UiModel.RequestLocationPermission)
    }

    @Test
    fun `observing LiveData launches check location`() {

        mainViewModel.model.observeForever(observer)

        mainViewModel.onCoarsePermissionRequested(true)

        verify(observer).onChanged(MainViewModel.UiModel.RequestCheckLocation)
    }

    @Test
    fun `observing LiveData launches check internet`() {

        mainViewModel.model.observeForever(observer)

        mainViewModel.onCoarsePermissionRequested(true)
        mainViewModel.checkLocation(true)

        verify(observer).onChanged(MainViewModel.UiModel.RequestCheckInternet)
    }

    @Test
    fun `after requesting the permission, loading is shown`() {
        runBlocking {

            whenever(getWeather.invoke()).thenReturn(mockedWeather)
            mainViewModel.model.observeForever(observer)

            mainViewModel.onCoarsePermissionRequested(true)
            mainViewModel.checkLocation(true)
            mainViewModel.checkInternet(true)

            verify(observer).onChanged(MainViewModel.UiModel.Loading)
        }
    }

    @Test
    fun `after requesting the permission, checking location and checking internet, getWeather is called`() {

        runBlocking {

            whenever(getWeather.invoke()).thenReturn(mockedWeather)
            mainViewModel.model.observeForever(observer)

            mainViewModel.onCoarsePermissionRequested(true)
            mainViewModel.checkLocation(true)
            mainViewModel.checkInternet(true)

            verify(observer).onChanged(MainViewModel.UiModel.RequestLocationPermission)
            verify(observer).onChanged(MainViewModel.UiModel.RequestCheckLocation)
            verify(observer).onChanged(MainViewModel.UiModel.RequestCheckInternet)
            verify(observer).onChanged(MainViewModel.UiModel.Loading)
            verify(observer).onChanged(MainViewModel.UiModel.Content(mockedWeather))
        }
    }

}