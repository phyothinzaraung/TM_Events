package dev.phyo.tm_events.data.remote.mediator

import android.content.Context
import android.os.Build
import android.util.Log
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
    private val context: Context
) : RemoteMediator<Int, EventEntity>() {

    private var lastPageFetched: Int = -1

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        return try {
            if (!NetworkUtils(context).isOnline()) {
                Log.d("RemoteMediator", "Device is offline. Loading from local database.")
                return MediatorResult.Success(endOfPaginationReached = false) // Prevents network call
            }
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    if (NetworkUtils(context).isOnline()) {
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
                    Log.d("RemoteMediator", "Fetched ${eventDtos.size} items for page $page")

                    val eventEntities = eventDtos.map { it.toEntity() }
                    eventDao.insertEvents(eventEntities)

                    val count = eventDao.getEventCount()
                    Log.d("RemoteMediator", "Total items in database: $count")

                    lastPageFetched = page

                    val endOfPaginationReached = eventDtos.size < state.config.pageSize
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                } else {
                    Log.d("RemoteMediator", "No more items to load for page $page")
                    MediatorResult.Success(endOfPaginationReached = true)
                }
            } else {
                Log.d("RemoteMediator", "API error: ${response.message()}")
                MediatorResult.Success(endOfPaginationReached = true)
            }
        } catch (e: Exception) {
            Log.d("RemoteMediator", "Unexpected error: ${e.message}")
            MediatorResult.Success(endOfPaginationReached = true)
        }
    }
}