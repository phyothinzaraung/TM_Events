package dev.phyo.tm_events.data.repository

import androidx.paging.PagingData
import dev.phyo.tm_events.data.helper.IEventHelper
import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.domain.repository.IEventRepository
import dev.phyo.tm_events.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class IEventRepositoryImpl(private val eventHelper: IEventHelper): IEventRepository {
    override suspend fun getEvents(city: String): Flow<DataResult<PagingData<Event>>> {
        return flow {
            emit(DataResult.Loading())

            eventHelper.getEvents(city).collect{
                emit(DataResult.Success(it))
            }
        }.catch { exception ->
            emit(DataResult.Error(exception.message ?: "unknown error"))
        }
    }
}