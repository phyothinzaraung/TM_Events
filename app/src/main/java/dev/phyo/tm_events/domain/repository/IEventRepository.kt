package dev.phyo.tm_events.domain.repository

import androidx.paging.PagingData
import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.util.DataResult
import kotlinx.coroutines.flow.Flow

interface IEventRepository {
    suspend fun getEvents(city: String): Flow<DataResult<PagingData<Event>>>
}