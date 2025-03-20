package dev.phyo.tm_events.presentation.events.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import dev.phyo.tm_events.presentation.utils.LoadingView
import dev.phyo.tm_events.presentation.events.viewmodel.EventsViewModel
import dev.phyo.tm_events.presentation.events.viewmodel.UIState
import dev.phyo.tm_events.presentation.utils.ErrorView

@Composable
fun EventScreen(eventsViewModel: EventsViewModel) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by eventsViewModel.searchQuery.collectAsStateWithLifecycle()

    when (val currentState = uiState) {
        is UIState.Success -> {
            val lazyPagingItems = currentState.data.collectAsLazyPagingItems()
            EventList(
                lazyPagingItems,
                onSearchQueryChanged = { query -> eventsViewModel.onSearchQueryChanged(query) },
                searchQuery = searchQuery
            )
        }
        is UIState.Error -> {
            ErrorView(currentState.message)
        }
        is UIState.Loading-> {
            LoadingView()
        }
    }
}