package dev.phyo.tm_events.data.mapper

import dev.phyo.tm_events.data.remote.model.City
import dev.phyo.tm_events.data.remote.model.Country
import dev.phyo.tm_events.data.remote.model.Dates
import dev.phyo.tm_events.data.remote.model.Embedded
import dev.phyo.tm_events.data.remote.model.EventDto
import dev.phyo.tm_events.data.remote.model.ImageDto
import dev.phyo.tm_events.data.remote.model.Start
import dev.phyo.tm_events.data.remote.model.State
import dev.phyo.tm_events.data.remote.model.Venue
import junit.framework.TestCase.assertEquals
import org.junit.Test

class EventDtoMapperTest {

    @Test
    fun `test EventDto to Entity mapping`(){
        //GIVEN
        val imageDto = ImageDto(
            url = "https://example.com/image.jpg",
            ratio = "16:9",
            width = 1920,
            height = 1080
        )

        val venue = Venue(
            name = "Main Hall",
            city = City(name = "New York"),
            state = State(name = "New York", stateCode = "NY"),
            country = Country(name = "United States", countryCode = "US")
        )

        val eventDto = EventDto(
            name = "Concert",
            imageDtos = listOf(imageDto),
            dates = Dates(start = Start(localDate = "2023-10-15", localTime = "19:00"), timezone = ""),
            embedded = Embedded(eventDtos= listOf(), venues = listOf(venue), )
        )

        //WHEN
        val eventEntity = eventDto.toEntity()

        //THEN
        assertEquals("Concert", eventEntity.name)
        assertEquals("https://example.com/image.jpg", eventEntity.imageUrl)
        assertEquals("16:9", eventEntity.imageRatio)
        assertEquals(1920, eventEntity.imageWidth)
        assertEquals(1080, eventEntity.imageHeight)
        assertEquals("Oct 15", eventEntity.eventDate)
        assertEquals("19:00", eventEntity.eventTime)
        assertEquals("Main Hall", eventEntity.venueName)
        assertEquals("New York", eventEntity.city)
        assertEquals("NY", eventEntity.stateCode)
    }

    @Test
    fun `test EventDto to Entity mapping with missing fields`(){
        //GIVEN
        val eventDto = EventDto(
            name = "Concert",
            imageDtos = emptyList(),
            dates = Dates(start = Start(localDate = "2023-10-15", localTime = "19:00"), timezone = ""),
            embedded = Embedded(eventDtos= listOf(), venues = listOf())
        )

        //WHEN
        val eventEntity = eventDto.toEntity()

        //THEN
        assertEquals("Concert", eventEntity.name)
        assertEquals("", eventEntity.imageUrl)
        assertEquals("", eventEntity.imageRatio)
        assertEquals(0, eventEntity.imageWidth)
        assertEquals(0, eventEntity.imageHeight)
        assertEquals("Oct 15", eventEntity.eventDate)
        assertEquals("19:00", eventEntity.eventTime)
        assertEquals("unknown", eventEntity.venueName)
        assertEquals("", eventEntity.city)
        assertEquals("", eventEntity.stateCode)
    }
}
