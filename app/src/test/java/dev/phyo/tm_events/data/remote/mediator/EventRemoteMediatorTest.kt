package dev.phyo.tm_events.data.remote.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingState
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.RemoteMediator
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.local.entity.EventEntity
import dev.phyo.tm_events.data.remote.model.ApiResponse
import dev.phyo.tm_events.data.remote.service.IEventService
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import retrofit2.Response

@OptIn(ExperimentalPagingApi::class)
class EventRemoteMediatorTest {

    private lateinit var mockEventService: IEventService
    private lateinit var mockEventDao: EventDao
    private lateinit var eventRemoteMediator: EventRemoteMediator

    @Before
    fun setup() {
        mockEventService = mockk()
        mockEventDao = mockk()

        eventRemoteMediator = EventRemoteMediator(mockEventService, mockEventDao)
    }


    @Test
    fun `test load with REFRESH and network is online`() = runTest {
        // GIVEN
        val mockPage = 0
        val mockState = PagingState<Int, EventEntity>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )
        val mockResponse = mockk<Response<ApiResponse>>()
        coEvery { mockEventService.getEvents(mockPage, 10) } returns mockResponse
        coEvery { mockResponse.isSuccessful } returns true
        coEvery { mockResponse.body()?.embedded?.eventDtos } returns listOf(mockk())

        // WHEN
        val result = eventRemoteMediator.load(LoadType.REFRESH, mockState)

        // THEN
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        val successResult = result as RemoteMediator.MediatorResult.Success
        assertTrue(successResult.endOfPaginationReached)
        coVerify { mockEventDao.clearEvents() }
    }

    @Test
    fun `test load with unsuccessful response`() = runTest {
        // GIVEN
        val mockPage = 0
        val mockState = PagingState<Int, EventEntity>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )
        val mockResponse = mockk<Response<ApiResponse>>()
        coEvery { mockEventService.getEvents(mockPage, 10) } returns mockResponse
        coEvery { mockResponse.isSuccessful } returns false

        // WHEN
        val result = eventRemoteMediator.load(LoadType.REFRESH, mockState)

        // THEN
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        val successResult = result as RemoteMediator.MediatorResult.Success
        assertTrue(successResult.endOfPaginationReached)
        coVerify(exactly = 0) { mockEventDao.insertEvents(any()) }
    }

    @Test
    fun `test load with empty event list`() = runTest {
        // GIVEN
        val mockPage = 0
        val mockState = PagingState<Int, EventEntity>(
            pages = emptyList(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 10),
            leadingPlaceholderCount = 0
        )
        val mockResponse = mockk<Response<ApiResponse>>()
        coEvery { mockEventService.getEvents(mockPage, 10) } returns mockResponse
        coEvery { mockResponse.isSuccessful } returns true
        coEvery { mockResponse.body()?.embedded?.eventDtos } returns emptyList()

        // WHEN
        val result = eventRemoteMediator.load(LoadType.REFRESH, mockState)

        // THEN
        assertTrue(result is RemoteMediator.MediatorResult.Success)
        val successResult = result as RemoteMediator.MediatorResult.Success
        assertTrue(successResult.endOfPaginationReached)
        coVerify(exactly = 0) { mockEventDao.insertEvents(any()) }
    }
}
