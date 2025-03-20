package dev.phyo.tm_events.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dev.phyo.tm_events.data.local.entity.EventEntity

@Dao
interface EventDao {

    @Query("SELECT * FROM events ORDER BY id ASC")
    fun getAllEvents(): PagingSource<Int, EventEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEvents(events: List<EventEntity>)

    @Query("DELETE FROM events")
    suspend fun clearEvents()

    @Query("SELECT * FROM events WHERE name LIKE '%' || :query || '%' OR venueName LIKE '%' || :query || '%' OR city LIKE '%' || :query || '%'")
    fun searchEvents(query: String):PagingSource<Int, EventEntity>

    @Query("SELECT COUNT(*) FROM events")
    fun getEventCount(): Int

}