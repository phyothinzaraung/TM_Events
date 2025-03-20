package dev.phyo.tm_events.util

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.cachedIn
import dev.phyo.tm_events.domain.model.Event
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class TestPagingSource : PagingSource<Int, Event>() {
    override fun getRefreshKey(state: PagingState<Int, Event>): Int? {
        return null
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        return LoadResult.Error(Throwable("An error occurred"))
    }
}

fun createErrorPagingData(): Flow<PagingData<Event>> {
    return Pager(
        config = PagingConfig(pageSize = 10),
        pagingSourceFactory = { TestPagingSource() }
    ).flow.cachedIn(CoroutineScope(Dispatchers.Main))
}