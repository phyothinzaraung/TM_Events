package dev.phyo.tm_events.domain.usecase

import androidx.paging.PagingData
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.repository.IEventRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class GetEventsUseCaseTest{

    private val eventRepository: IEventRepository = mockk()
    private lateinit var getEventsUseCase: GetEventsUseCase

    @Before
    fun setUp(){
        getEventsUseCase = GetEventsUseCase(eventRepository)
    }

    @Test
    fun `invoke should return events flow`() = runTest {
        // GIVEN
        val query = "music"
        val dummyEvents = listOf(
            Event(1, "Concert A", "url1", "2024-04-01", "19:00", "Venue A", "City A", "CA"),
            Event(2, "Concert B", "url2", "2024-05-01", "20:00", "Venue B", "City B", "NY")
        )
        val expectedFlow = flowOf(PagingData.from(dummyEvents))

        coEvery { eventRepository.getEvents(query) } returns expectedFlow

        // WHEN
        val result = getEventsUseCase(query)

        // THEN
        assertEquals(expectedFlow, result)
    }

}