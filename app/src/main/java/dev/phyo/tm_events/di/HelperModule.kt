package dev.phyo.tm_events.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.phyo.tm_events.data.helper.IEventHelperImpl
import dev.phyo.tm_events.data.helper.IEventHelper
import dev.phyo.tm_events.data.service.IEventService

@Module
@InstallIn(ViewModelComponent::class)
object HelperModule {

    @Provides
    fun providesEventHelper(eventService: IEventService): IEventHelper = IEventHelperImpl(eventService)
}