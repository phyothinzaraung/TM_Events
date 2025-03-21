package dev.phyo.tm_events.data.remote.mediator

import dev.phyo.tm_events.data.remote.model.ApiResponse
import dev.phyo.tm_events.data.remote.model.City
import dev.phyo.tm_events.data.remote.model.Country
import dev.phyo.tm_events.data.remote.model.Dates
import dev.phyo.tm_events.data.remote.model.Embedded
import dev.phyo.tm_events.data.remote.model.EventDto
import dev.phyo.tm_events.data.remote.model.ImageDto
import dev.phyo.tm_events.data.remote.model.Page
import dev.phyo.tm_events.data.remote.model.Start
import dev.phyo.tm_events.data.remote.model.State
import dev.phyo.tm_events.data.remote.model.Venue

val fakeApiResponse = ApiResponse(
    embedded = Embedded(
        eventDtos = listOf(
            EventDto(
                name = "Concert Night",
                imageDtos = listOf(
                    ImageDto(url = "https://example.com/image1.jpg", ratio = "16:9", width = 1920, height = 1080),
                    ImageDto(url = "https://example.com/image2.jpg", ratio = "4:3", width = 1024, height = 768)
                ),
                dates = Dates(
                    start = Start(
                        localDate = "2025-06-15",
                        localTime = "19:30:00"
                    ),
                    timezone = ""
                ),
                embedded = Embedded(
                    eventDtos = emptyList(),
                    venues = emptyList()
                )
            )
        ),
        venues = listOf(
            Venue(
                name = "Staples Center",
                city = City(name = "Los Angeles"),
                state = State(name = "California", stateCode = "CA"),
                country = Country(name = "United States", countryCode = "US")
            )
        )
    ),
    page = Page(
        size = 20,
        totalElements = 100,
        totalPages = 5,
        number = 1
    )
)
