package dev.phyo.tm_events.data.model

data class Venue(
    val name: String,
    val city: City,
    val state: State,
    val country: Country
)

data class City(
    val name: String
)

data class State(
    val name: String,
    val stateCode: String
)

data class Country(
    val name: String,
    val countryCode: String
)
