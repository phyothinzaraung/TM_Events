package dev.phyo.tm_events.presentation.events.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.presentation.utils.ErrorView
import dev.phyo.tm_events.presentation.utils.LoadingView
import dev.phyo.tm_events.presentation.utils.PullToRefreshBox
import dev.phyo.tm_events.presentation.utils.SearchView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventList(
    eventList: LazyPagingItems<Event>,
    onSearchQueryChanged: (String) -> Unit,
    searchQuery: String,
    modifier: Modifier = Modifier
) {
    var isSearching by remember { mutableStateOf(false) }
    val isRefreshing = eventList.loadState.refresh is LoadState.Loading

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        SearchView(
                            searchQuery = searchQuery,
                            onSearchQueryChanged = onSearchQueryChanged,
                            onCloseSearch = { isSearching = false }
                        )
                    } else {
                        Text("Simple TM Events List")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        isSearching = !isSearching
                        if (!isSearching) onSearchQueryChanged("")
                    }) {
                        Icon(
                            imageVector = if (isSearching) Icons.Default.Close else Icons.Default.Search,
                            contentDescription = if (isSearching) "Close Search" else "Search"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        PullToRefreshBox(
            isRefreshing = isRefreshing,
            onRefresh = { eventList.refresh() }
        ) {
            LazyColumn(
                modifier = modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                items(eventList.itemCount) { index ->
                    val event = eventList[index]
                    if (event != null) {
                        EventItem(event)
                    }
                }

                eventList.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item { LoadingView() }
                        }

                        loadState.append is LoadState.Loading -> {
                            item { LoadingView() }
                        }

                        loadState.refresh is LoadState.Error -> {
                            item {
                                val error = (loadState.refresh as LoadState.Error).error
                                ErrorView(error.localizedMessage ?: "An error occurred")
                            }
                        }
                    }
                }
            }
        }
    }
}