package dev.phyo.tm_events.data.helper

import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.data.service.IEventService
import retrofit2.Response

class IEventHelperImpl(private val eventService: IEventService): IEventHelper {
    override suspend fun getEvents(city: String): Response<List<Event>> {
        val response = eventService.getEvents(city)
        val events = response.body()?._embedded?.events ?: emptyList()
        return Response.success(events)
    }
}