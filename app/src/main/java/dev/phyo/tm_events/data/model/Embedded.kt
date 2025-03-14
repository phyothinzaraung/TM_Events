package dev.phyo.tm_events.data.model

data class Embedded(
    val events: List<Event>,
    val venues: List<Venue>
)
