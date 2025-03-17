package dev.phyo.tm_events.data.remote.model

import com.google.gson.annotations.SerializedName

data class Embedded(
    @SerializedName("events")
    val eventDtos: List<EventDto>,
    @SerializedName("venues")
    val venues: List<Venue>
)
