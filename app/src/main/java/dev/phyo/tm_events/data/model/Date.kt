package dev.phyo.tm_events.data.model

data class Dates(
    val start: Start,
    val timezone: String
)

data class Start(
    val localDate: String,
    val localTime: String
)
