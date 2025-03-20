package dev.phyo.tm_events.data.repository

import androidx.paging.PagingData
import dev.phyo.tm_events.data.helper.IEventHelper
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.repository.IEventRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*

import org.junit.Before
import org.junit.Test

class IEventRepositoryImplTest {

    private lateinit var eventRepository: IEventRepository
    private val eventHelper: IEventHelper = mockk()

    @Before
    fun setUp() {
        eventRepository = IEventRepositoryImpl(eventHelper)
    }

    @Test
    fun `getEvents should return events from eventHelper`() = runTest {
        // GIVEN
        val query = "sports"
        val dummyEvents = listOf(
            Event(1, "Match A", "url1", "2024-06-01", "18:00", "Stadium A", "City A", "CA"),
            Event(2, "Match B", "url2", "2024-07-01", "20:00", "Stadium B", "City B", "NY")
        )
        val expectedFlow = flowOf(PagingData.from(dummyEvents))

        coEvery { eventHelper.getEvents(query) } returns expectedFlow

        // WHEN
        val result = eventRepository.getEvents(query)

        // THEN
        assertEquals(expectedFlow, result)
    }
}