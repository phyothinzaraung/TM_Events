package dev.phyo.tm_events.domain.model

data class Event(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val eventDate: String,
    val eventTime: String,
    val venueName: String,
    val city: String,
    val stateCode: String
)
