package dev.phyo.tm_events.data.remote.mediator

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.mapper.toEntity
import dev.phyo.tm_events.data.remote.service.IEventService
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator(
    private val eventService: IEventService,
    private val eventDao: EventDao,
    private val city: String
) : RemoteMediator<Int, EventEntity>() {

    private var lastPageFetched: Int = -1

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        return try {
            val page = when (loadType) {
                LoadType.REFRESH -> {
                    eventDao.clearEvents()
                    lastPageFetched = -1
                    0
                }
                LoadType.PREPEND -> {
                    return MediatorResult.Success(endOfPaginationReached = true)
                }
                LoadType.APPEND -> {
                    lastPageFetched + 1
                }
            }

            val response = eventService.getEvents(city, page, state.config.pageSize)
            if (response.isSuccessful) {
                val eventDtos = response.body()?.embedded?.eventDtos
                if (!eventDtos.isNullOrEmpty()) {

                    Log.d("RemoteMediator", "Fetched ${eventDtos.size} items for page $page")

                    val eventEntities = eventDtos.map { it.toEntity() }
                    eventDao.insertEvents(eventEntities)

                    lastPageFetched = page

                    val endOfPaginationReached = eventDtos.size < state.config.pageSize
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                } else {
                    Log.d("RemoteMediator", "No more items to load for page $page")
                    MediatorResult.Success(endOfPaginationReached = true)
                }
            } else {
                Log.d("RemoteMediator", "API error: ${response.message()}")
                MediatorResult.Error(IOException("Failed to fetch data from API"))
            }
        } catch (e: Exception) {
            Log.d("RemoteMediator", "Unexpected error: ${e.message}")
            MediatorResult.Error(e)
        }
    }
}