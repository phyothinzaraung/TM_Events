package dev.phyo.tm_events.presentation.events

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.phyo.tm_events.data.model.Event
import dev.phyo.tm_events.domain.usecase.GetEventsUseCase
import dev.phyo.tm_events.util.DataResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EventsViewModel @Inject constructor(private val getEventsUseCase: GetEventsUseCase): ViewModel() {
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> get() = _uiState

    init {
        getEvents("Sacramento")
    }

    private fun getEvents(city: String){
        viewModelScope.launch {
            getEventsUseCase(city).collect { result ->
                _uiState.value = when (result) {
                    is DataResult.Success -> {
                        UIState.Success(result.data)
                    }
                    is DataResult.Error -> {
                        UIState.Error(result.message ?: "An error occurred")
                    }
                    is DataResult.Loading -> {
                        UIState.Loading
                    }
                }
            }
        }
    }
}

sealed interface UIState{
    data object Loading: UIState
    data class Success(val eventList: Flow<PagingData<Event>>): UIState
    data class Error(val message: String): UIState
}