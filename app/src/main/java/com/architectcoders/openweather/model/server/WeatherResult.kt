package com.architectcoders.openweather.model.server

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

class WeatherResult(
    @SerializedName("coord")
    @Expose
    var coord: Coord,
    @SerializedName("weather")
    @Expose
    var weather: List<Weather>,
    @SerializedName("base")
    @Expose
    var base: String,
    @SerializedName("main")
    @Expose
    var main: Main,
    @SerializedName("wind")
    @Expose
    var wind: Wind,
    @SerializedName("clouds")
    @Expose
    var clouds: Clouds,
    @SerializedName("dt")
    @Expose
    var dt: Int,
    @SerializedName("sys")
    @Expose
    var sys: Sys,
    @SerializedName("timezone")
    @Expose
    var timezone: Int,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("name")
    @Expose
    var name: String,
    @SerializedName("cod")
    @Expose
    var cod: Int
)

class Clouds(
    @SerializedName("all")
    @Expose
    private var all: Int
)

class Coord(
    @SerializedName("lon")
    @Expose
    private var lon: Integer,
    @SerializedName("lat")
    @Expose
    private var lat: Integer
)


class Main(
    @SerializedName("temp")
    @Expose
    var temp: Double,
    @SerializedName("pressure")
    @Expose
    var pressure: Int,
    @SerializedName("humidity")
    @Expose
    var humidity: Int,
    @SerializedName("temp_min")
    @Expose
    var tempMin: Double,
    @SerializedName("temp_max")
    @Expose
    var tempMax: Double
)

class Sys(
    @SerializedName("type")
    @Expose
    var type: Int,
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("message")
    @Expose
    var message: Double,
    @SerializedName("country")
    @Expose
    var country: String,
    @SerializedName("sunrise")
    @Expose
    var sunrise: Int,
    @SerializedName("sunset")
    @Expose
    var sunset: Int
)

class Weather(
    @SerializedName("id")
    @Expose
    var id: Int,
    @SerializedName("main")
    @Expose
    var main: String,
    @SerializedName("description")
    @Expose
    var description: String,
    @SerializedName("icon")
    @Expose
    var icon: String
)

class Wind(
    @SerializedName("speed")
    @Expose
    var speed: Double,
    @SerializedName("deg")
    @Expose
    var deg: Double
)