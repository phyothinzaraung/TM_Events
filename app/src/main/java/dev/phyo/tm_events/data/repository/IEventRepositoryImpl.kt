package dev.phyo.tm_events.data.repository

import androidx.paging.PagingData
import dev.phyo.tm_events.data.helper.IEventHelper
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.repository.IEventRepository
import kotlinx.coroutines.flow.Flow

class IEventRepositoryImpl(
    private val eventHelper: IEventHelper
) : IEventRepository {

    override suspend fun getEvents(query: String): Flow<PagingData<Event>> {
        return eventHelper.getEvents(query)
    }
}