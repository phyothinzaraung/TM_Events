package dev.phyo.tm_events.data.helper

import dev.phyo.tm_events.data.model.Event
import retrofit2.Response

interface IEventHelper {
    suspend fun getEvents(city: String): Response<List<Event>>
}