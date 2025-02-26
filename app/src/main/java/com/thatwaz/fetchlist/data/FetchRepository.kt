package com.thatwaz.fetchlist.data

import com.thatwaz.fetchlist.data.model.FetchItem
import com.thatwaz.fetchlist.data.remote.FetchApiService

class FetchRepository(private val apiService: FetchApiService) {

    suspend fun getFilteredSortedItems(): List<FetchItem> {
        return apiService.getItems()
            .filter { !it.name.isNullOrBlank() } // Remove blank/null names
            .sortedWith(compareBy(
                { it.listId }, // First, sort by listId
                { extractNumber(it.name!!) } // Then, sort numerically by the number in "Item X"
            ))
    }

    private fun extractNumber(name: String): Int {
        return name.filter { it.isDigit() }.toIntOrNull() ?: Int.MAX_VALUE
    }
}

