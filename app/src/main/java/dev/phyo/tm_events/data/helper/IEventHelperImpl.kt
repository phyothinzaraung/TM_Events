package dev.phyo.tm_events.data.helper

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.mapper.toDomain
import dev.phyo.tm_events.data.remote.mediator.EventRemoteMediator
import dev.phyo.tm_events.data.remote.service.IEventService
import dev.phyo.tm_events.domain.model.Event
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class IEventHelperImpl(
    private val eventService: IEventService,
    private val eventDao: EventDao
): IEventHelper {
    @OptIn(ExperimentalPagingApi::class)
    override fun getEvents(city: String): Flow<PagingData<Event>> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = EventRemoteMediator(eventService, eventDao, city),
            pagingSourceFactory = {eventDao.getAllEvents()}
        ).flow.map { pagingData ->
            pagingData.map { it.toDomain() }
        }
    }

}