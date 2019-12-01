package com.architectcoders.openweather.ui

import android.content.Context
import android.util.AttributeSet
import android.widget.TextView
import androidx.core.text.bold
import com.architectcoders.openweather.model.detail.Detail

class WeatherDetailInfoView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : TextView(context, attrs, defStyleAttr) {

    fun setMovie(detail: Detail) = with(detail) {
        text = androidx.core.text.buildSpannedString {

            bold { append("Description: ") }
            appendln(description)

            bold { append("Temperature: ") }
            appendln(temperature)

            bold { append("Pressure: ") }
            appendln(pressure)

            bold { append("Humidity: ") }
            appendln(humidity)

            bold { append("Temperature Min: ") }
            appendln(temp_min)

            bold { append("Temperature Maximum: ") }
            appendln(temp_max)
        }
    }
}