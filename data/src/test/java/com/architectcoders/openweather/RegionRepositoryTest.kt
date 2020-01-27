package com.architectcoders.openweather

import com.architectcoders.data.repository.PermissionChecker
import com.architectcoders.data.repository.RegionRepository
import com.architectcoders.data.source.LocationDataSource
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class RegionRepositoryTest {


    val lonOrLat = "12.21"

    @Mock
    lateinit var locationDataSource: LocationDataSource

    @Mock
    lateinit var permissionChecker: PermissionChecker

    private lateinit var regionRepository: RegionRepository

    @Before
    fun setUp() {
        regionRepository = RegionRepository(locationDataSource, permissionChecker)
    }

    @Test
    fun `returns default Lat when coarse permission not granted`() {
        runBlocking {

            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(false)

            val region = regionRepository.findLat()

            Assert.assertEquals(RegionRepository.DEFAULT_LAT, region)
        }
    }

    @Test
    fun `returns default Lon when coarse permission not granted`() {
        runBlocking {

            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(false)

            val region = regionRepository.findlon()

            Assert.assertEquals(RegionRepository.DEFAULT_LON, region)
        }
    }


    @Test
    fun `returns lat from location data source when permission granted`() {
        runBlocking {

            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.findLat()).thenReturn(lonOrLat)

            val region = regionRepository.findLat()

            Assert.assertEquals(lonOrLat, region)
        }
    }

    @Test
    fun `returns lon from location data source when permission granted`() {
        runBlocking {

            whenever(permissionChecker.check(PermissionChecker.Permission.COARSE_LOCATION)).thenReturn(true)
            whenever(locationDataSource.findLon()).thenReturn(lonOrLat)

            val region = regionRepository.findlon()

            Assert.assertEquals(lonOrLat, region)
        }
    }
}