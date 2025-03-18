package dev.phyo.tm_events.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.usecase.GetEventsUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(
    private val getEventsUseCase: GetEventsUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> get() = _uiState

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> get() = _searchQuery

    private val _eventsFlow = MutableStateFlow<Flow<PagingData<Event>>?>(null)
    val eventsFlow: StateFlow<Flow<PagingData<Event>>?> get() = _eventsFlow


    fun getEvents(query: String = "") {
        viewModelScope.launch {
            UIState.Loading
            try {
                _searchQuery.value = query
                val events = getEventsUseCase(query)
                _eventsFlow.value = events
                _uiState.value = UIState.Success
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "An error occurred")
            }
        }
    }

    fun onSearchQueryChanged(newQuery: String) {
        _searchQuery.value = newQuery
    }
}

sealed interface UIState {
    data object Loading : UIState
    data object Success : UIState
    data class Error(val message: String) : UIState
}