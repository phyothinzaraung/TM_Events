package dev.phyo.tm_events.presentation.events.viewmodel

import androidx.paging.PagingData
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.usecase.GetEventsUseCase
import dev.phyo.tm_events.utils.MainCoroutineRule
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
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

    @Before
    fun setUp() {
        eventsViewModel = EventsViewModel(getEventsUseCase)
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