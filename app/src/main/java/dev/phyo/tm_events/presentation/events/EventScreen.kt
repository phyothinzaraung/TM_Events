package dev.phyo.tm_events.presentation.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems


@Composable
fun EventScreen(eventsViewModel: EventsViewModel) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()

    when (val currentState = uiState) {
        is UIState.Loading -> {
            Loading()
        }
        is UIState.Success -> {
            val lazyPagingItems = currentState.eventList.collectAsLazyPagingItems()
            EventList(lazyPagingItems)
        }
        is UIState.Error -> {
            Error(currentState.message)
        }
    }
}