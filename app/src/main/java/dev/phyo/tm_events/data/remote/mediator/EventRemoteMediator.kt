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
import dev.phyo.tm_events.util.NetworkUtils

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator(
    private val eventService: IEventService,
    private val eventDao: EventDao,
    private val networkUtils: NetworkUtils
) : RemoteMediator<Int, EventEntity>() {

    private var lastPageFetched: Int = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        return try {
            if (!networkUtils.isOnline()) {
                return MediatorResult.Success(endOfPaginationReached = false)
            }
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    if (networkUtils.isOnline()) {
                        eventDao.clearEvents()
                        lastPageFetched = -1
                        0
                    } else {
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    lastPageFetched + 1
                }
            }

            val response = eventService.getEvents(page, state.config.pageSize)
            if (response.isSuccessful) {
                val eventDtos = response.body()?.embedded?.eventDtos
                if (!eventDtos.isNullOrEmpty()) {
                    val eventEntities = eventDtos.map { it.toEntity() }
                    eventDao.insertEvents(eventEntities)
                    lastPageFetched = page

                    val endOfPaginationReached = eventDtos.size < state.config.pageSize
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                } else {
                    MediatorResult.Success(endOfPaginationReached = true)
                }
            } else {
                MediatorResult.Success(endOfPaginationReached = true)
            }
        } catch (e: Exception) {
            MediatorResult.Success(endOfPaginationReached = true)
        }
    }
}