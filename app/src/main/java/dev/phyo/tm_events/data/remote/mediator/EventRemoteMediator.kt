package dev.phyo.tm_events.data.remote.mediator

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.mapper.toEntity
import dev.phyo.tm_events.data.remote.service.IEventService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator(
    private val eventService: IEventService,
    private val eventDao: EventDao
) : RemoteMediator<Int, EventEntity>() {

    private var lastPageFetched: Int = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                lastPageFetched = -1
                0
            }
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> lastPageFetched + 1
        }

        return try {
            val response = eventService.getEvents(page, state.config.pageSize)
            if (response.isSuccessful) {
                val eventDtos = response.body()?.embedded?.eventDtos
                if (!eventDtos.isNullOrEmpty()) {
                    val eventEntities = eventDtos.map { it.toEntity() }

                    withContext(Dispatchers.IO) {
                        eventDao.insertEvents(eventEntities)
                    }

                    lastPageFetched = page
                    MediatorResult.Success(endOfPaginationReached = eventDtos.size < state.config.pageSize)
                } else {
                    MediatorResult.Success(endOfPaginationReached = true)
                }
            } else {
                fallbackToDatabase()
            }
        } catch (e: Exception) {
            fallbackToDatabase()
        }
    }

    private suspend fun fallbackToDatabase(): MediatorResult {
        return withContext(Dispatchers.IO) {
            val localDataCount = eventDao.getEventCount()
            if (localDataCount > 0) {
                MediatorResult.Success(endOfPaginationReached = false)
            } else {
                MediatorResult.Success(endOfPaginationReached = true)
            }
        }
    }
}


