package dev.phyo.tm_events.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.phyo.tm_events.data.remote.model.EventDto
import dev.phyo.tm_events.domain.model.Event
import dev.phyo.tm_events.domain.usecase.GetEventsUseCase
import dev.phyo.tm_events.util.DataResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val getEventsUseCase: GetEventsUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> get() = _uiState

    private val _eventsFlow = MutableStateFlow<Flow<PagingData<Event>>?>(null)
    val eventsFlow: StateFlow<Flow<PagingData<Event>>?> get() = _eventsFlow

    init {
        getEvents("")
    }

    private fun getEvents(city: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            try {
                val events = getEventsUseCase(city)
                _eventsFlow.value = events
                _uiState.value = UIState.Success
            } catch (e: Exception) {
                _uiState.value = UIState.Error(e.message ?: "An error occurred")
            }
        }
    }

//    private fun getEvents(city: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            getEventsUseCase(city).collect { result ->
//                _uiState.value = when (result) {
//                    is DataResult.Success -> {
//                        UIState.Success(result.data)
//                    }
//                    is DataResult.Error -> {
//                        UIState.Error(result.message ?: "An error occurred")
//                    }
//                    is DataResult.Loading -> {
//                        UIState.Loading
//                    }
//                }
//            }
//        }
//    }
}

sealed interface UIState{
    data object Loading: UIState
    data object Success: UIState
    data class Error(val message: String): UIState
}