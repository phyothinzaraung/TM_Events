package dev.phyo.tm_events.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.local.entity.EventEntity

@Database(entities = [EventEntity::class], version = 1, exportSchema = false)
abstract class EventDatabase: RoomDatabase() {
    abstract fun eventDao(): EventDao
}