package dev.phyo.tm_events.data.model

import com.google.gson.annotations.SerializedName

data class Event(
    val name: String,
    val images: List<Image>,
    val dates: Dates,
    @SerializedName("_embedded")
    val embedded: Embedded,

    )
