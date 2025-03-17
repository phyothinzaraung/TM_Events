package dev.phyo.tm_events.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.local.database.EventDatabase
import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.mapper.toEntity
import dev.phyo.tm_events.data.remote.model.EventDto
import dev.phyo.tm_events.data.remote.service.IEventService
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediator(
    private val eventService: IEventService,
    private val eventDao: EventDao,
    private val city: String
): RemoteMediator<Int, EventEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, EventEntity>
    ): MediatorResult {
        return try {
            val page = when(loadType){
                LoadType.REFRESH -> 0
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null){
                        return MediatorResult.Success(endOfPaginationReached = true)
                    }
                    (lastItem.id / state.config.pageSize) + 1
                }
            }

            val response = eventService.getEvents(city, page, state.config.pageSize)
            if (response.isSuccessful){
                val eventDtos = response.body()?.embedded?.eventDtos
                if (!eventDtos.isNullOrEmpty()){
                    val eventEntities = eventDtos.map { it.toEntity() }
                    eventDao.insertEvents(eventEntities)

                    val endOfPaginationReached = eventDtos.size < state.config.pageSize
                    MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
                } else {
                    MediatorResult.Success(endOfPaginationReached = true)
                }
            }else{
                MediatorResult.Error(IOException("Failed to fetch data from API"))
            }
        }catch (e: Exception){
            MediatorResult.Error(e)
        }
    }
}