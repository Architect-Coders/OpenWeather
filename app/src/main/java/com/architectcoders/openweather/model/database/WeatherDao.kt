package com.architectcoders.openweather.model.database

import androidx.room.*

@Dao
interface WeatherDao {

    @Query("SELECT * FROM Weather")
    fun getAll(): List<Weather>

    @Query("SELECT * FROM Weather WHERE id = :id")
    fun findById(id: Int): Weather

    @Query("SELECT * FROM Weather WHERE timestamp = :timestamp")
    fun findByTimestamp(timestamp: String): Weather

    @Query("SELECT * FROM Weather WHERE city = :city")
    fun findByCity(city: String): List<Weather>

    @Query("SELECT COUNT(id) FROM Weather")
    fun weatherCount(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertWeather(weather: Weather)

    @Update
    fun updateWeather(weather: Weather)
}