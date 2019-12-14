package com.architectcoders.openweather.ui.common.customview

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.text.bold
import com.architectcoders.openweather.R
import com.architectcoders.openweather.model.database.Weather
import com.architectcoders.openweather.model.detail.Detail

class WeatherDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    fun setWeather(weather: Weather) = with(weather) {
        text = androidx.core.text.buildSpannedString {

            bold { append(context.getString(R.string.detail_description)) }
            appendln(description)

            bold { append(context.getString(R.string.detail_temperature)) }
            appendln(temperature)

            bold { append(context.getString(R.string.detail_pressure)) }
            appendln(pressure)

            bold { append(context.getString(R.string.detail_humidity)) }
            appendln(humidity)

            bold { append(context.getString(R.string.detail_temperature_min)) }
            appendln(temp_min)

            bold { append(context.getString(R.string.detail_temperature_maximum)) }
            appendln(temp_max)
        }
    }
}