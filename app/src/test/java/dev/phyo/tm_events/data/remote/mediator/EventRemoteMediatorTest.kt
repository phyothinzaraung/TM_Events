package dev.phyo.tm_events.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingState
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.RemoteMediator
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.local.database.EventDatabase
import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.remote.model.ApiResponse
import dev.phyo.tm_events.data.remote.service.IEventService
import io.mockk.*
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediatorTest {

    private lateinit var mockEventService: IEventService
    private lateinit var mockEventDao: EventDao
    private lateinit var mockEventDatabase: EventDatabase
    private lateinit var eventRemoteMediator: EventRemoteMediator

    @Before
    fun setup() {
        mockEventService = mockk(relaxed = true)
        mockEventDao = mockk(relaxed = true)
        mockEventDatabase = mockk(relaxed = true)

        every { mockEventDatabase.eventDao() } returns mockEventDao

        eventRemoteMediator = EventRemoteMediator(mockEventService, mockEventDao)

        every { mockEventDao.getEventCount() } returns 0
    }


    @Test
    fun `load REFRESH should return success with zero events from API`() = runBlocking {
        // GIVEN
        val mockApiResponse = mockk<Response<ApiResponse>> {
            every { isSuccessful } returns true
            every { body() } returns mockk {
                every { embedded } returns mockk {
                    every { eventDtos } returns emptyList()
                }
            }
        }

        // WHEN
        coEvery { mockEventService.getEvents(0, 20) } returns mockApiResponse
        val pagingState = mockk<PagingState<Int, EventEntity>>(relaxed = true)
        val mediatorResult = eventRemoteMediator.load(LoadType.REFRESH, pagingState)

        // THEN
        assertTrue(mediatorResult is RemoteMediator.MediatorResult.Success)
        assertTrue((mediatorResult as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load APPEND should return Success and insert events into DB`() = runTest {
        // GIVEN
        val testPagingState = PagingState<Int, EventEntity>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(
                pageSize = 20,
                prefetchDistance = 2,
                enablePlaceholders = false
            ),
            leadingPlaceholderCount = 0
        )

        // WHEN
        coEvery { mockEventService.getEvents(any(), any()) } returns Response.success(
            fakeApiResponse
        )
        coEvery { mockEventDao.insertEvents(any()) } just Runs

        val mediatorResult = eventRemoteMediator.load(LoadType.APPEND, testPagingState)

        assertTrue(mediatorResult is RemoteMediator.MediatorResult.Success)
        assertTrue((mediatorResult as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        coVerify { mockEventDao.insertEvents(any()) }
    }

    @Test
    fun `load APPEND should return Success and fallback to database on failure`() = runBlocking {

        coEvery { mockEventService.getEvents(eq(1), any()) } throws Exception("API error")

        val pagingState = mockk<PagingState<Int, EventEntity>>(relaxed = true)
        val mediatorResult = eventRemoteMediator.load(LoadType.APPEND, pagingState)

        assertTrue(mediatorResult is RemoteMediator.MediatorResult.Success)
        assertTrue((mediatorResult as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load PREPEND should return Success with endOfPaginationReached = true`() = runBlocking {
        val pagingState = mockk<PagingState<Int, EventEntity>>(relaxed = true)
        val mediatorResult = eventRemoteMediator.load(LoadType.PREPEND, pagingState)

        assertTrue(mediatorResult is RemoteMediator.MediatorResult.Success)
        assertTrue((mediatorResult as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `load REFRESH should return Success when events are available from API`() = runBlocking {

        coEvery { mockEventService.getEvents(eq(0), any()) } returns Response.success(
            fakeApiResponse
        )

        val pagingState = mockk<PagingState<Int, EventEntity>>(relaxed = true)
        val mediatorResult = eventRemoteMediator.load(LoadType.REFRESH, pagingState)

        assertTrue(mediatorResult is RemoteMediator.MediatorResult.Success)
        assertFalse((mediatorResult as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        coVerify { mockEventDao.insertEvents(any()) }
    }
}
