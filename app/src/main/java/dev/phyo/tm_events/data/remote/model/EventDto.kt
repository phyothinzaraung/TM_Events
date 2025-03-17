package dev.phyo.tm_events.data.remote.model

import com.google.gson.annotations.SerializedName

data class EventDto(
    @SerializedName("name")
    val name: String,
    @SerializedName("images")
    val imageDtos: List<ImageDto>,
    @SerializedName("dates")
    val dates: Dates,
    @SerializedName("_embedded")
    val embedded: Embedded,
    )
