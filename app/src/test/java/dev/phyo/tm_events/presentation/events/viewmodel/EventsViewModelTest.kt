package dev.phyo.tm_events.presentation.events.viewmodel

import androidx.paging.PagingData
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.usecase.GetEventsUseCase
import dev.phyo.tm_events.util.NetworkUtils
import dev.phyo.tm_events.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class EventsViewModelTest {

    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var eventsViewModel: EventsViewModel
    private val getEventsUseCase: GetEventsUseCase = mockk()
    private val networkUtils: NetworkUtils = mockk()

    private val mockEvents: Flow<PagingData<Event>> = flowOf(
        PagingData.from(
            listOf(
                Event(
                    id = 1,
                    name = "Tech Conference 2023",
                    imageUrl = "https://example.com/tech-conference.jpg",
                    eventDate = "2023-11-15",
                    eventTime = "09:00 AM",
                    venueName = "Convention Center",
                    city = "San Francisco",
                    stateCode = "CA"
                )
            )
        )
    )

    private fun generateDummyEvents(size: Int): Flow<PagingData<Event>> {
        val events = List(size) { index ->
            Event(
                id = index,
                name = "Event $index",
                imageUrl = "https://example.com/event$index.jpg",
                eventDate = "2025-01-01",
                eventTime = "18:00",
                venueName = "Venue $index",
                city = "City $index",
                stateCode = "ST"
            )
        }
        return flowOf(PagingData.from(events))
    }

    @Before
    fun setUp() {
        every { networkUtils.isOnline() } returns true
        eventsViewModel = EventsViewModel(getEventsUseCase, networkUtils)
    }

    @Test
    fun `getEvents should emit Loading and then Success`() = coroutineRule.testScope.runTest {
        val dummyEvents = flowOf(PagingData.from(listOf(Event(1, "Event 1", "url", "2024-03-20", "10:00 AM", "Venue", "City", "TX"))))
        coEvery { getEventsUseCase(any()) } returns dummyEvents

        eventsViewModel.getEvents("test")

        assertEquals(UIState.Loading, eventsViewModel.uiState.value)

        advanceUntilIdle()

        assertTrue(eventsViewModel.uiState.value is UIState.Success)
    }

    @Test
    fun `getEvents should emit Loading and then Error when exception occurs`() = coroutineRule.testScope.runTest {
        coEvery { getEventsUseCase(any()) } throws Exception("Network error")

        eventsViewModel.getEvents("test")

        assertEquals(UIState.Loading, eventsViewModel.uiState.value)

        advanceUntilIdle()

        assertTrue(eventsViewModel.uiState.value is UIState.Error)
    }
}