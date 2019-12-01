package com.architectcoders.openweather.model.image

import android.content.Context
import android.graphics.drawable.Drawable

class CreateImage {
    fun getImageFromString(
        main: String,
        context: Context
    ): Drawable? {
        val imageMain: SelectImage
        when (main) {
            "Clear" -> {
                imageMain = SelectImage(ImageMain.SUNNY)
            }
            "Drizzle" -> {
                imageMain = SelectImage(ImageMain.RAINY)
            }
            "Rain" -> {
                imageMain = SelectImage(ImageMain.RAINY)
            }
            "Mist" -> {
                imageMain = SelectImage(ImageMain.PARTLY_CLOUDY)
            }
            "Clouds" -> {
                imageMain = SelectImage(ImageMain.CLOUDY)
            }
            "Snow" -> {
                imageMain = SelectImage(ImageMain.SNOWY)
            }
            "Extreme" -> {
                imageMain = SelectImage(ImageMain.SNOWY)
            }
            else -> {
                imageMain = SelectImage(ImageMain.SUNNY)
            }
        }
        return imageMain.chooseImage(context)
    }
}