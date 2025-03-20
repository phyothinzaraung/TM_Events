package dev.phyo.tm_events.data.mapper

import dev.phyo.tm_events.data.local.entity.EventEntity
import org.junit.Assert.*
import org.junit.Test

class EventEntityMapperTest{
    @Test
    fun `test EventEntity to Domain mapping`(){
        //GIVEN
        val eventEntity = EventEntity(
            id = 1,
            name = "Concert",
            imageUrl = "https://example.com/image.jpg",
            eventDate = "2023-10-15",
            eventTime = "19:00",
            venueName = "Main Hall",
            city = "New York",
            stateCode = "NY"
        )

        //WHEN
        val event = eventEntity.toDomain()

        //THEN
        assertEquals(1, event.id)
        assertEquals("Concert", event.name)
        assertEquals("https://example.com/image.jpg", event.imageUrl)
        assertEquals("2023-10-15", event.eventDate)
        assertEquals("19:00", event.eventTime)
        assertEquals("Main Hall", event.venueName)
        assertEquals("New York", event.city)
        assertEquals("NY", event.stateCode)
    }

    @Test
    fun `test EventEntity to Domain mapping with null values`(){
        //GIVEN
        val eventEntity = EventEntity(
            id = 1,
            name = "Concert",
            imageUrl = null,
            eventDate = null,
            eventTime = null,
            venueName = null,
            city = null,
            stateCode = null
        )

        //WHEN
        val event = eventEntity.toDomain()

        //THEN
        assertEquals(1, event.id)
        assertEquals("Concert", event.name)
        assertEquals("", event.imageUrl)
        assertEquals("", event.eventDate)
        assertEquals("", event.eventTime)
        assertEquals("", event.venueName)
        assertEquals("", event.city)
        assertEquals("", event.stateCode)
    }
}