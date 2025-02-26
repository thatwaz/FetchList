package com.thatwaz.fetchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thatwaz.fetchlist.data.FetchRepository
import com.thatwaz.fetchlist.data.model.FetchItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FetchViewModel(private val repository: FetchRepository) : ViewModel() {

    private val _items = MutableStateFlow<List<FetchItem>>(emptyList())
    val items: StateFlow<List<FetchItem>> = _items

    private val _expandedSections = MutableStateFlow<Set<Int>>(emptySet())
    val expandedSections: StateFlow<Set<Int>> = _expandedSections

    init {
        fetchItems()
    }

    fun fetchItems() {
        viewModelScope.launch {
            _items.value = repository.getFilteredSortedItems()
        }
    }


    fun updateExpandedSections(newSet: Set<Int>) {
        _expandedSections.value = newSet
    }
}

