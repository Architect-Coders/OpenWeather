package com.architectcoders.openweather.ui.common

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.architectcoders.openweather.R
import com.architectcoders.openweather.WeatherApp
import com.architectcoders.openweather.model.image.ImageMain
import java.text.SimpleDateFormat
import java.util.*
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
            return chooseImage(context, ImageMain.RAINY)
        }
        "Rain" -> {
            return chooseImage(context, ImageMain.RAINY)
        }
        "Clouds" -> {
            return chooseImage(context, ImageMain.CLOUDY)
        }
        "Snow" -> {
            return chooseImage(context, ImageMain.SNOWY)
        }
        "Extreme" -> {
            return chooseImage(context, ImageMain.SNOWY)
        }
        "Mist" -> {
            return chooseImage(context, ImageMain.FOG)
        }
        "Fog" -> {
            return chooseImage(context, ImageMain.FOG)
        }
        else -> {
            return chooseImage(context, ImageMain.SUNNY)
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
        ImageMain.FOG -> ContextCompat.getDrawable(context, R.drawable.ic_fog)
        ImageMain.RAINY -> ContextCompat.getDrawable(context, R.drawable.ic_rainning)
        ImageMain.SNOWY -> ContextCompat.getDrawable(context, R.drawable.ic_snowy)
    }
}

@Suppress("UNCHECKED_CAST")
inline fun <reified T : ViewModel> FragmentActivity.getViewModel(crossinline factory: () -> T): T {

    val vmFactory = object : ViewModelProvider.Factory {
        override fun <U : ViewModel> create(modelClass: Class<U>): U = factory() as U
    }

    return ViewModelProviders.of(this, vmFactory)[T::class.java]
}


val Context.app: WeatherApp
    get() = applicationContext as WeatherApp

fun getDateTime(timestamp: String): String? {
    return try {
        val sdf = SimpleDateFormat("HH:mm\ndd/MM")
        val netDate = Date(timestamp.toLong() * 1000)
        sdf.format(netDate)
    } catch (e: Exception) {
        e.toString()
    }
}