package com.architectcoders.openweather.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Weather::class], version = 1)
abstract class WeatherDatabase : RoomDatabase() {

    abstract fun weatherDao(): WeatherDao

    companion object {
        fun build(context: Context) = Room.databaseBuilder(
            context,
            WeatherDatabase::class.java, "weather-db"
        ).build()
    }
}