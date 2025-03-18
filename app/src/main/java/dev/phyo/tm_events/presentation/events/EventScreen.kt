package dev.phyo.tm_events.presentation.events

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.collectAsLazyPagingItems
import dev.phyo.tm_events.util.NetworkUtils

@Composable
fun EventScreen(eventsViewModel: EventsViewModel, networkUtils: NetworkUtils, context: Context) {
    val uiState by eventsViewModel.uiState.collectAsStateWithLifecycle()
    val eventsFlow = eventsViewModel.eventsFlow.collectAsStateWithLifecycle()
    val searchQuery by eventsViewModel.searchQuery.collectAsStateWithLifecycle()
    val isOffline by remember {
        mutableStateOf(!networkUtils.isOnline())
    }

    LaunchedEffect(Unit) {
        eventsViewModel.getEvents()
    }

    LaunchedEffect(searchQuery) {
        eventsViewModel.getEvents(searchQuery)
    }

    LaunchedEffect(isOffline) {
        if (isOffline) {
            Toast.makeText(context, "Device is offline", Toast.LENGTH_SHORT).show()
        }
    }

    when (val currentState = uiState) {
        is UIState.Loading -> {
            LoadingView()
        }
        is UIState.Success -> {
            val lazyPagingItems = eventsFlow.value?.collectAsLazyPagingItems()
            Column {
                if (lazyPagingItems != null) {
                    EventList(
                        lazyPagingItems,
                        onSearchQueryChanged = { query -> eventsViewModel.onSearchQueryChanged(query) },
                        searchQuery = searchQuery
                    )
                } else {
                    EmptyView()
                }
            }
        }
        is UIState.Error -> {
            ErrorView(currentState.message)
        }
    }
}