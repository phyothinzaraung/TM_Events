package dev.phyo.tm_events.domain.usecase

import androidx.paging.PagingData
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.repository.IEventRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetEventsUseCase @Inject constructor(
    private val eventRepository: IEventRepository
    ) {
    suspend operator fun invoke(query: String): Flow<PagingData<Event>>{
        return eventRepository.getEvents(query)
    }
}