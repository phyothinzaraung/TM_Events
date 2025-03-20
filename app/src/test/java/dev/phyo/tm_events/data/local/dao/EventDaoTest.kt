package dev.phyo.tm_events.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import dev.phyo.tm_events.data.local.database.EventDatabase
import dev.phyo.tm_events.data.local.entity.EventEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class EventDaoTest {

    private lateinit var database: EventDatabase
    private lateinit var eventDao: EventDao

    @Before
    fun setUp() {

        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            EventDatabase::class.java,
        ).allowMainThreadQueries().build()

        eventDao = database.eventDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun testInsertAndGetAllEvents() = runBlocking {
        //GIVEN
        val event1 = EventEntity(
            id = 1,
            name = "Concert",
            imageUrl = "https://example.com/image1.jpg",
            eventDate = "2023-10-15",
            eventTime = "19:00",
            venueName = "Main Hall",
            city = "New York",
            stateCode = "NY"
        )
        val event2 = EventEntity(
            id = 2,
            name = "Festival",
            imageUrl = "https://example.com/image2.jpg",
            eventDate = "2023-11-20",
            eventTime = "18:00",
            venueName = "Park Arena",
            city = "Los Angeles",
            stateCode = "CA"
        )

        //WHEN
        eventDao.insertEvents(listOf(event1, event2))
        val pagingSource = eventDao.getAllEvents()
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        //Then
        assertTrue(result is PagingSource.LoadResult.Page)
        val events = (result as PagingSource.LoadResult.Page).data
        assertEquals(events.size, 2)
        assertEquals(event1, events[0])
        assertEquals(event2, events[1])
    }

    @Test
    fun testClearEvents() = runBlocking {
        //GIVEN
        val event = EventEntity(
            id = 1,
            name = "Concert",
            imageUrl = "https://example.com/image1.jpg",
            eventDate = "2023-10-15",
            eventTime = "19:00",
            venueName = "Main Hall",
            city = "New York",
            stateCode = "NY"
        )
        eventDao.insertEvents(listOf(event))

        //WHEN
        eventDao.clearEvents()
        val pagingSource = eventDao.getAllEvents()
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        //THEN
        assertTrue(result is PagingSource.LoadResult.Page)
        val events = (result as PagingSource.LoadResult.Page).data
        assertTrue(events.isEmpty())
    }

    @Test
    fun testSearchEvents() = runBlocking {
        // GIVEN
        val event1 = EventEntity(
            id = 1,
            name = "Concert",
            imageUrl = "https://example.com/image1.jpg",
            eventDate = "2023-10-15",
            eventTime = "19:00",
            venueName = "Main Hall",
            city = "New York",
            stateCode = "NY"
        )
        val event2 = EventEntity(
            id = 2,
            name = "Festival",
            imageUrl = "https://example.com/image2.jpg",
            eventDate = "2023-11-20",
            eventTime = "18:00",
            venueName = "Park Arena",
            city = "Los Angeles",
            stateCode = "CA"
        )
        eventDao.insertEvents(listOf(event1, event2))

        // WHEN
        val pagingSource = eventDao.searchEvents("Concert")
        val result = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = 10,
                placeholdersEnabled = false
            )
        )

        // THEN
        assertTrue(result is PagingSource.LoadResult.Page)
        val events = (result as PagingSource.LoadResult.Page).data
        assertEquals(1, events.size)
        assertEquals(event1, events[0])
    }
}