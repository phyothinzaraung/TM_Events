package dev.phyo.tm_events.utils

import androidx.paging.PagingData
import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.phyo.tm_events.data.local.entity.EventEntity

class FakePagingSource(private val data: List<EventEntity>) : PagingSource<Int, EventEntity>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EventEntity> {
        return LoadResult.Page(
            data = data,
            prevKey = null,
            nextKey = null
        )
    }

    override fun getRefreshKey(state: PagingState<Int, EventEntity>): Int? {
        return null
    }
}
