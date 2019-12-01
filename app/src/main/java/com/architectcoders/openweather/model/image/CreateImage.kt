package com.architectcoders.openweather.model.image

import android.content.Context
import android.graphics.drawable.Drawable




class CreateImage {
    fun getImageFromString(
        main: String,
        context: Context?
    ): Drawable? {
        val imageMain: SelectImage
        if (main == "Clear") {
            imageMain = SelectImage(ImageMain.SUNNY)
        } else if (main == "Drizzle") {
            imageMain = SelectImage(ImageMain.RAINY)
        } else if (main == "Rain") {
            imageMain = SelectImage(ImageMain.RAINY)
        } else if (main == "Mist") {
            imageMain = SelectImage(ImageMain.PARTLY_CLOUDY)
        } else if (main == "Clouds") {
            imageMain = SelectImage(ImageMain.CLOUDY)
        } else if (main == "Snow") {
            imageMain = SelectImage(ImageMain.SNOWY)
        } else if (main == "Extreme") {
            imageMain = SelectImage(ImageMain.SNOWY)
        } else {
            imageMain = SelectImage(ImageMain.SUNNY)
        }
        return imageMain.chooseImage(context)
    }
}