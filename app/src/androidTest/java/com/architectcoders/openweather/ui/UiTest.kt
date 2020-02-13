package com.architectcoders.openweather.ui

import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.rule.ActivityTestRule
import androidx.test.rule.GrantPermissionRule
import com.architectcoders.openweather.R
import com.architectcoders.openweather.ui.main.MainActivity
import com.architectcoders.openweather.data.server.WeatherDB
import com.architectcoders.openweather.utils.fromJson
import com.jakewharton.espresso.OkHttp3IdlingResource
import okhttp3.mockwebserver.MockResponse
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.test.KoinTest
import org.koin.test.get

class UiTest : KoinTest {

    @get:Rule
    val mockWebServerRule = MockWebServerRule()

    @get:Rule
    val activityTestRule = ActivityTestRule(MainActivity::class.java, false, false)

    @get:Rule
    val grantPermissionRule: GrantPermissionRule =
        GrantPermissionRule.grant(
            "android.permission.ACCESS_COARSE_LOCATION"
        )

    @Before
    fun setUp() {
        mockWebServerRule.server.enqueue(
            MockResponse().fromJson(
                ApplicationProvider.getApplicationContext(),
                "weather.json"
            )
        )

        val resource = OkHttp3IdlingResource.create("OkHttp", get<WeatherDB>().okHttpClient)
        IdlingRegistry.getInstance().register(resource)
    }

    @Test
    fun clickAWeatherNavigatesToDetail() {
        activityTestRule.launchActivity(null)

        onView(ViewMatchers.withId(R.id.location_city)).perform(click())

        onView(ViewMatchers.withId(R.id.weatherDetailSummary))
            .check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.weatherDetailSummary))
            .check(matches(withText("Clear")))
        onView(ViewMatchers.withId(R.id.weatherDetailToolbar))
            .check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.weatherDetailToolbar))
            .check(matches(ViewMatchers.hasDescendant(withText("Tarancon"))))
    }
}