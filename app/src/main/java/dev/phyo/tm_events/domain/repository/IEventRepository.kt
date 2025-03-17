package dev.phyo.tm_events.domain.repository

import androidx.paging.PagingData
import dev.phyo.tm_events.data.remote.model.EventDto
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.util.DataResult
import kotlinx.coroutines.flow.Flow

interface IEventRepository {
//    suspend fun getEvents(city: String): Flow<DataResult<PagingData<Event>>>
    suspend fun getEvents(city: String): Flow<PagingData<Event>>
}