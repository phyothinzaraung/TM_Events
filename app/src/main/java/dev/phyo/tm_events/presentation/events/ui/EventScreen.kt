package dev.phyo.tm_events.presentation.events.ui

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import dev.phyo.tm_events.presentation.utils.EmptyView
import dev.phyo.tm_events.presentation.utils.ErrorView
import dev.phyo.tm_events.presentation.utils.LoadingView
import dev.phyo.tm_events.presentation.events.viewmodel.EventsViewModel
import dev.phyo.tm_events.presentation.events.viewmodel.UIState

@Composable
fun EventScreen(eventsViewModel: EventsViewModel) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by eventsViewModel.searchQuery.collectAsStateWithLifecycle()
    val isOffline by eventsViewModel.isOffline.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(searchQuery, isOffline) {
        eventsViewModel.getEvents(searchQuery)
        if (isOffline) {
            Toast.makeText(context, "Device is offline", Toast.LENGTH_SHORT).show()
        }
    }

    when (val currentState = uiState) {
        is UIState.Loading -> {
            LoadingView()
        }
        is UIState.Success -> {
            val lazyPagingItems = currentState.data.collectAsLazyPagingItems()
            when(lazyPagingItems.loadState.refresh){
                is LoadState.Loading -> LoadingView()
                is LoadState.NotLoading -> {
                    if (lazyPagingItems.itemCount == 0){
                        EmptyView()
                    }else{
                        EventList(
                            lazyPagingItems,
                            onSearchQueryChanged = { query -> eventsViewModel.onSearchQueryChanged(query) },
                            searchQuery = searchQuery
                        )
                    }
                }
                is LoadState.Error -> ErrorView("Failed to load data")
            }
        }
        is UIState.Error -> {
            ErrorView(currentState.message)
        }
    }
}