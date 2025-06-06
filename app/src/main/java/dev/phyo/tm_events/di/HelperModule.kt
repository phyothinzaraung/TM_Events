package dev.phyo.tm_events.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.phyo.tm_events.data.helper.IEventHelperImpl
import dev.phyo.tm_events.data.helper.IEventHelper
import dev.phyo.tm_events.data.local.dao.EventDao
import dev.phyo.tm_events.data.remote.service.IEventService

@Module
@InstallIn(ViewModelComponent::class)
object HelperModule {

    @Provides
    fun providesEventHelper(eventService: IEventService, eventDao: EventDao): IEventHelper = IEventHelperImpl(eventService, eventDao)
}