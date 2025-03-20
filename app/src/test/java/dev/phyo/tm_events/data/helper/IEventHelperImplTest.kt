package dev.phyo.tm_events.data.helper

import androidx.paging.PagingSource
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.remote.service.IEventService
import io.mockk.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test

class IEventHelperImplTest {

    private lateinit var eventHelper: IEventHelperImpl
    private lateinit var mockEventService: IEventService
    private lateinit var mockEventDao: EventDao

    @Before
    fun setup() {
        mockEventService = mockk()
        mockEventDao = mockk()
        eventHelper = IEventHelperImpl(mockEventService, mockEventDao)
    }

    @Test
    fun `test getEvents with non-empty query calls searchEvents`() = runTest {
        // GIVEN
        val query = "test"
        val fakePagingSource = mockk<PagingSource<Int, EventEntity>>(relaxed = true)
        coEvery { mockEventDao.searchEvents(query) } returns fakePagingSource

        // WHEN
        eventHelper.getEvents(query).first()

        // THEN
        coVerify { mockEventDao.searchEvents(query) }
        coVerify(exactly = 0) { mockEventDao.getAllEvents() }
    }

    @Test
    fun `test getEvents with empty query calls getAllEvents`() = runTest {
        // GIVEN
        val query = ""

        val fakePagingSource = mockk<PagingSource<Int, EventEntity>>(relaxed = true)
        coEvery { mockEventDao.getAllEvents() } returns fakePagingSource

        // WHEN
        eventHelper.getEvents(query).first()

        // THEN
        coVerify { mockEventDao.getAllEvents() }
        coVerify(exactly = 0) { mockEventDao.searchEvents(any()) }
    }
}

