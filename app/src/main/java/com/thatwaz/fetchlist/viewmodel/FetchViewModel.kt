package com.thatwaz.fetchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thatwaz.fetchlist.data.FetchRepository
import com.thatwaz.fetchlist.data.model.FetchItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FetchViewModel(private val repository: FetchRepository) : ViewModel() {
    sealed class UiState {
        object Loading : UiState()
        data class Error(val message: String) : UiState()
        data class Success(val items: List<FetchItem>) : UiState()
    }

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState

    init {
        fetchItems()
    }

    fun fetchItems() { // ✅ This function is now public, so it can be called on Retry
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            try {
                val data = repository.getFilteredSortedItems()
                _uiState.value = UiState.Success(data)
            } catch (e: Exception) {
                _uiState.value = UiState.Error("Failed to load data. Check your connection.")
            }
        }
    }
}




