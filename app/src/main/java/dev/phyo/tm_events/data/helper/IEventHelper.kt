package dev.phyo.tm_events.data.helper

import androidx.paging.PagingData
import androidx.paging.PagingSource
import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.remote.model.EventDto
import dev.phyo.tm_events.domain.model.Event
import kotlinx.coroutines.flow.Flow

interface IEventHelper {
    fun getEvents(query: String): Flow<PagingData<Event>>
}