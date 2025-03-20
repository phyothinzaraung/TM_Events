package dev.phyo.tm_events.data.helper

import androidx.paging.map
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.remote.service.IEventService
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.util.NetworkUtils
import dev.phyo.tm_events.utils.FakePagingSource
import io.mockk.*
import kotlinx.coroutines.test.*
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class IEventHelperImplTest {

    private lateinit var eventHelper: IEventHelperImpl
    private lateinit var mockEventService: IEventService
    private lateinit var mockEventDao: EventDao
    private lateinit var mockNetworkUtils: NetworkUtils

    @Before
    fun setup() {
        // Initialize the mocks
        mockEventService = mockk()
        mockEventDao = mockk()
        mockNetworkUtils = mockk()

        // Create the instance of the class under test
        eventHelper = IEventHelperImpl(mockEventService, mockEventDao, mockNetworkUtils)
    }

    @Test
    fun `test getEvents with query`() = runTest {
        // GIVEN
        val query = "test"
        val fakeEventList = listOf(
            EventEntity(1, "Test Event")
        )

        coEvery { mockEventDao.searchEvents(query) } returns FakePagingSource(fakeEventList)

        //WHEN
        val flow = eventHelper.getEvents(query)

        val eventsList = mutableListOf<Event>()
        flow.collect { pagingData ->
            pagingData.map { event ->
                eventsList.add(event)
            }
        }

        // THEN
        assertEquals(1, eventsList.size)
        assertEquals("Test Event", eventsList[0].name)
    }

    @Test
    fun `test getEvents with empty query`() = runTest {
        // Given
        val query = ""
        val fakeEventList = listOf(EventEntity(1, "Test Event"))
        coEvery { mockEventDao.getAllEvents() } returns FakePagingSource(fakeEventList)
        coEvery { mockNetworkUtils.isOnline() } returns true

        // When
        val flow = eventHelper.getEvents(query)

        val eventsList = mutableListOf<Event>()
        flow.collect { pagingData ->
            pagingData.map { event ->
                eventsList.add(event)
            }
        }

        // Then
        assertEquals(1, eventsList.size)
        assertEquals("Test Event", eventsList[0].name)
        coVerify { mockEventDao.getAllEvents() }
    }
}

