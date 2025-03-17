package dev.phyo.tm_events.presentation.events

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import dev.phyo.tm_events.domain.model.Event

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EventList(eventList: LazyPagingItems<Event>, modifier: Modifier = Modifier) {
    var searchQuery by remember { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }

    val isRefreshing = eventList.loadState.refresh is LoadState.Loading

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (isSearching) {
                        Box(Modifier.fillMaxWidth()) {
                            TextField(
                                value = searchQuery,
                                onValueChange = { searchQuery = it },
                                placeholder = { Text("Search...") },
                                singleLine = true,
                                modifier = Modifier.fillMaxWidth(),
                                keyboardOptions = KeyboardOptions.Default.copy(
                                    imeAction = ImeAction.Done
                                ),
                                keyboardActions = KeyboardActions(
                                    onDone = { isSearching = false }
                                )
                            )
                        }
                    } else {
                        Text("Simple TM Events List")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        if (isSearching) {
                            searchQuery = ""
                            isSearching = false
                        } else {
                            isSearching = true
                        }
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
            if (eventList.loadState.refresh is LoadState.Error) {
                val errorMessage = (eventList.loadState.refresh as LoadState.Error).error.localizedMessage
                Error(errorMessage ?: "An error occurred")
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    items(eventList.itemCount) { index ->
                        val event = eventList[index]
                        if (event != null &&
                            (searchQuery.isEmpty() || event.name.contains(
                                searchQuery,
                                ignoreCase = true
                            ) || event.venueName.contains(
                                searchQuery,
                                ignoreCase = true
                            ))
                        ) {
                            EventItem(event)
                        }
                    }

                    when {
                        eventList.loadState.append is LoadState.Loading -> {
                            item { Loading() }
                        }
                    }
                }
            }
        }
    }
}

