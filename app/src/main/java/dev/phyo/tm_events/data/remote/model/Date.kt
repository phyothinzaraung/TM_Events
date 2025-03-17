package dev.phyo.tm_events.data.remote.model

import com.google.gson.annotations.SerializedName

data class Dates(
    @SerializedName("start")
    val start: Start,
    @SerializedName("timezone")
    val timezone: String
)

data class Start(
    @SerializedName("localDate")
    val localDate: String,
    @SerializedName("localTime")
    val localTime: String
)
