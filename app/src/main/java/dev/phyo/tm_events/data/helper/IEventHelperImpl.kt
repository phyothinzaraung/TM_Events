package dev.phyo.tm_events.data.helper

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.data.paging.EventPagingSource
import dev.phyo.tm_events.data.remote.IEventService
import kotlinx.coroutines.flow.Flow

class IEventHelperImpl(private val eventService: IEventService): IEventHelper {
    override suspend fun getEvents(city: String): Flow<PagingData<Event>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                EventPagingSource(eventService, city)
            }
        ).flow
    }
}