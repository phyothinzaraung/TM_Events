package dev.phyo.tm_events.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import dev.phyo.tm_events.data.helper.IEventHelper
import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.data.remote.IEventService

class EventPagingSource(
    private val eventService: IEventService,
    private val city: String
): PagingSource<Int, Event>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Event> {
        return try {
            val currentPage = params.key ?: 0
            val response = eventService.getEvents(city, currentPage)

            if (response.isSuccessful){
                val events = response.body()?.embedded?.events ?: emptyList()

                LoadResult.Page(
                    data = events,
                    prevKey = if (currentPage == 0) null else currentPage - 1,
                    nextKey = if (events.isEmpty()) null else currentPage + 1
                )
            }else{
                LoadResult.Error(Exception("Error: ${response.message()}"))
            }
        }catch (e: Exception){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Event>): Int? {
        return state.anchorPosition?.let { state.closestPageToPosition(it)?.prevKey?.plus(1) }
    }


}