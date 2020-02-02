package com.architectcoders.openweather.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.architectcoders.openweather.initMockedDi
import com.architectcoders.openweather.testshared.mockedWeather
import com.architectcoders.openweather.ui.main.MainViewModel
import com.architectcoders.usescases.GetWeather
import com.nhaarman.mockitokotlin2.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainIntegrationTests : AutoCloseKoinTest() {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    lateinit var observer: Observer<MainViewModel.UiModel>

    private lateinit var vm: MainViewModel

    @Before
    fun setUp() {
        val vmModule = module {
            factory { MainViewModel(get(), get()) }
            factory { GetWeather(get()) }
        }

        initMockedDi(vmModule)
        vm = get()
    }

    @Test
    fun `data is loaded from server`() {
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested(true)
        vm.checkLocation(true)
        vm.checkInternet(true)

        verify(observer).onChanged(MainViewModel.UiModel.Content(mockedWeather))
    }

    @Test
    fun `show message turn on permission when permission is denegate `() {
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested(false)

        verify(observer).onChanged(MainViewModel.UiModel.ShowTurnOnPermission)
    }

    @Test
    fun `show message turn on location when location is off `() {
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested(true)
        vm.checkLocation(false)

        verify(observer).onChanged(MainViewModel.UiModel.ShowTurnOnLocation)
    }

    @Test
    fun `show message turn on internet when there is not internet`() {
        vm.model.observeForever(observer)

        vm.onCoarsePermissionRequested(true)
        vm.checkLocation(true)
        vm.checkInternet(false)

        verify(observer).onChanged(MainViewModel.UiModel.ShowCanCheckYourInternet)
    }
}