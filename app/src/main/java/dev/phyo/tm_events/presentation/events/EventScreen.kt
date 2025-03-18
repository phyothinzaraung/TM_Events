package dev.phyo.tm_events.presentation.events

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun EventScreen(eventsViewModel: EventsViewModel) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    val eventsFlow = eventsViewModel.eventsFlow.collectAsStateWithLifecycle()
    val searchQuery by eventsViewModel.searchQuery.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        eventsViewModel.getEvents()
    }

    LaunchedEffect(searchQuery) {
        eventsViewModel.getEvents(searchQuery)
    }

    when (val currentState = uiState) {
        is UIState.Loading -> {
            LoadingView()
        }
        is UIState.Success -> {
            val lazyPagingItems = eventsFlow.value?.collectAsLazyPagingItems()
            if (lazyPagingItems != null){
                EventList(
                    lazyPagingItems,
                    onSearchQueryChanged = {query -> eventsViewModel.onSearchQueryChanged(query)},
                    searchQuery = searchQuery
                )
            }else{
                EmptyView()
            }
        }
        is UIState.Error -> {
            ErrorView(currentState.message)
        }
    }
}