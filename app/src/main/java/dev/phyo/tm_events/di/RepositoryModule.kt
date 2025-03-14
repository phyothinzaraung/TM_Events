package dev.phyo.tm_events.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dev.phyo.tm_events.data.helper.IEventHelper
import dev.phyo.tm_events.data.repository.IEventRepositoryImpl
import dev.phyo.tm_events.domain.repository.IEventRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    fun providesEventRepository(eventHelper: IEventHelper): IEventRepository = IEventRepositoryImpl(eventHelper)
}