package dev.phyo.tm_events.data.repository

import dev.phyo.tm_events.data.helper.IEventHelper
import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.domain.repository.IEventRepository
import dev.phyo.tm_events.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class IEventRepositoryImpl(private val eventHelper: IEventHelper): IEventRepository {
    override suspend fun getEvents(city: String): Flow<DataResult<List<Event>>> {
        return flow<DataResult<List<Event>>> {
            emit(DataResult.Loading())

            with(eventHelper.getEvents(city)){
                if (isSuccessful){
                    emit(DataResult.Success(this.body()))
                }else{
                    emit(DataResult.Error(this.message()))
                }
            }
        }.catch { exception ->
            emit(DataResult.Error(exception.message))
        }
    }
}