package com.architectcoders.openweather.model.image

import android.R
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


class SelectImage {
    var mWeather: ImageMain? = null

    fun SelectImage(weather: ImageMain?) {
        mWeather = weather
    }

    fun chooseImage(context: Context?): Drawable? {
        return when (mWeather) {
            ImageMain.SUNNY -> ContextCompat.getDrawable(context, R.drawable.ic_sunny)
            ImageMain.CLOUDY -> ContextCompat.getDrawable(context, R.drawable.ic_cloudy)
            ImageMain.PARTLY_CLOUDY -> ContextCompat.getDrawable(
                context,
                R.drawable.ic_partly_cloudy
            )
            ImageMain.RAINY -> ContextCompat.getDrawable(context, R.drawable.ic_rainning)
            ImageMain.SNOWY -> ContextCompat.getDrawable(context, R.drawable.ic_snowy)
            else -> ContextCompat.getDrawable(context, R.drawable.ic_sunny)
        }
    }
}