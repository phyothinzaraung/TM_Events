package dev.phyo.tm_events.domain.usecase

import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.domain.repository.IEventRepository
import dev.phyo.tm_events.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(private  val eventRepository: IEventRepository) {
    operator fun invoke(city: String): Flow<DataResult<List<Event>>>{
        return flow {
            eventRepository.getEvents(city).collect{
                when(it){
                    is DataResult.Success -> {emit(DataResult.Success(it.data ?: emptyList()))}
                    is DataResult.Error -> {emit(DataResult.Error(it.message))}
                    is DataResult.Loading -> {emit(DataResult.Loading())}
                }
            }
        }
    }
}