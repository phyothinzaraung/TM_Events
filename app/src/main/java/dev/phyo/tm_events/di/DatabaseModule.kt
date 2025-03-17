package dev.phyo.tm_events.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.local.database.EventDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesEventDao(db: EventDatabase): EventDao = db.eventDao()

    @Provides
    @Singleton
    fun providesEventDatabase(@ApplicationContext context: Context): EventDatabase{
        return Room.databaseBuilder(
            context,
            EventDatabase::class.java,
            "event.db"
        ).build()
    }
}