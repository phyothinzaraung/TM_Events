package dev.phyo.tm_events.domain.repository

import androidx.paging.PagingData
import dev.phyo.tm_events.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface IEventRepository {
    suspend fun getEvents(query: String): Flow<PagingData<Event>>
}