package dev.phyo.tm_events.data.model

data class Event(
    val name: String,
    val images: List<Image>,
    val dates: Dates,
    val _embedded: Embedded,

    )
