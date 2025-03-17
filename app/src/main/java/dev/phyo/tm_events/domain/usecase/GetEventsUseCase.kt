package dev.phyo.tm_events.domain.usecase

import androidx.paging.PagingData
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.repository.IEventRepository
import dev.phyo.tm_events.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventRepository: IEventRepository
    ) {
    suspend operator fun invoke(city: String): Flow<PagingData<Event>>{
        return eventRepository.getEvents(city)
//        return flow {
//            eventRepository.getEvents(city).collect{
//                when(it){
//                    is DataResult.Success -> {emit(DataResult.Success(flow { emit(it.data) }))}
//                    is DataResult.Error -> {emit(DataResult.Error(it.message))}
//                    is DataResult.Loading -> {emit(DataResult.Loading())}
//                }
//            }
//        }
    }
}