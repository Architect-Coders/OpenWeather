package com.architectcoders.openweather.model.detail

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class  Detail (
    @SerializedName("city") val city: String,
    @SerializedName("main") val main: String,
    @SerializedName("description") val description: String,
    @SerializedName("temperature") val temperature: String,
    @SerializedName("pressure") val pressure: String,
    @SerializedName("humidity") val humidity: String,
    @SerializedName("temp_min") val temp_min: String,
    @SerializedName("temp_max") val temp_max: String
    ) : Parcelable