package dev.phyo.tm_events.data.helper

import androidx.paging.PagingData
import dev.phyo.tm_events.data.model.Event
import kotlinx.coroutines.flow.Flow

interface IEventHelper {
    suspend fun getEvents(city: String): Flow<PagingData<Event>>
}