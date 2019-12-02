package com.architectcoders.openweather.ui.commun

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.openweather.R
import com.architectcoders.openweather.model.image.ImageMain
import kotlin.properties.Delegates



inline fun <reified T : Activity> Context.intentFor(body: Intent.() -> Unit): Intent =
    Intent(this, T::class.java).apply(body)

inline fun <reified T : Activity> Context.startActivity(body: Intent.() -> Unit) {
    startActivity(intentFor<T>(body))
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)


inline fun <VH : RecyclerView.ViewHolder, T> RecyclerView.Adapter<VH>.basicDiffUtil(
    initialValue: List<T>,
    crossinline areItemsTheSame: (T, T) -> Boolean = { old, new -> old == new },
    crossinline areContentsTheSame: (T, T) -> Boolean = { old, new -> old == new }
) =
    Delegates.observable(initialValue) { _, old, new ->
        DiffUtil.calculateDiff(object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areItemsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
                areContentsTheSame(old[oldItemPosition], new[newItemPosition])

            override fun getOldListSize(): Int = old.size

            override fun getNewListSize(): Int = new.size
        }).dispatchUpdatesTo(this@basicDiffUtil)
    }

fun getImageFromString(
    main: String,
    context: Context
): Drawable? {
    when (main) {
        "Clear" -> {
            return chooseImage(context, ImageMain.SUNNY)
        }
        "Drizzle" -> {
            return chooseImage(context,ImageMain.RAINY)
        }
        "Rain" -> {
            return chooseImage(context,ImageMain.RAINY)
        }
        "Mist" -> {
            return chooseImage(context,ImageMain.PARTLY_CLOUDY)
        }
        "Clouds" -> {
            return chooseImage(context,ImageMain.CLOUDY)
        }
        "Snow" -> {
            return chooseImage(context,ImageMain.SNOWY)
        }
        "Extreme" -> {
            return chooseImage(context,ImageMain.SNOWY)
        }
        else -> {
            return chooseImage(context,ImageMain.SUNNY)
        }
    }
}

fun chooseImage(context: Context, weather: ImageMain): Drawable? {
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