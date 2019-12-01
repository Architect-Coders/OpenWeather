package com.architectcoders.openweather.model.image

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import com.architectcoders.openweather.R


class SelectImage(var weather: ImageMain) {


    fun chooseImage(context: Context): Drawable? {
        return when (weather) {
            ImageMain.SUNNY -> ContextCompat.getDrawable(context, R.drawable.ic_sunny)
            ImageMain.CLOUDY -> ContextCompat.getDrawable(context, R.drawable.ic_cloudy)
            ImageMain.PARTLY_CLOUDY -> ContextCompat.getDrawable(
                context,
                R.drawable.ic_partly_cloudy
            )
            ImageMain.RAINY -> ContextCompat.getDrawable(context, R.drawable.ic_rainning)
            ImageMain.SNOWY -> ContextCompat.getDrawable(context, R.drawable.ic_snowy)
        }
    }
}