package com.thatwaz.fetchlist.data

import com.thatwaz.fetchlist.data.model.FetchItem
import com.thatwaz.fetchlist.data.remote.FetchApiService

/**
 * Repository responsible for fetching and processing data from the Fetch API.
 *
 * This repository retrieves a list of [FetchItem]s from the API, filters out any
 * items with blank or null names, and sorts them based on:
 * 1. `listId` (ascending order).
 * 2. The number in the `name` field (numerically, not lexicographically).
 *
 * ### Example Sorting Logic:
 * - "Item 2" appears **before** "Item 12" (since `2 < 12`).
 * - Items from `listId = 1` appear **before** those from `listId = 2`.
 *
 * @param apiService The API service interface for fetching data.
 */
class FetchRepository(private val apiService: FetchApiService) {

    /**
     * Fetches and processes the list of items from the API.
     *
     * - Filters out items where `name` is blank or null.
     * - Sorts the items first by `listId`, then numerically by the number in `name`.
     *
     * @return A **sorted** and **filtered** list of [FetchItem]s.
     */
    suspend fun getFilteredSortedItems(): List<FetchItem> {
        return apiService.getItems()
            .filter { !it.name.isNullOrBlank() } // Remove blank/null names
            .sortedWith(compareBy(
                { it.listId }, // First, sort by listId
                { extractNumber(it.name!!) } // Then, sort numerically by the number in "Item X"
            ))
    }

    /**
     * Extracts the numeric portion from an item name.
     *
     * This method finds any digits in the item's name and converts them into an integer.
     * If no number is found, it returns **Int.MAX_VALUE** to push the item to the end of the list.
     *
     * @param name The item name (assumed to be non-null at this point).
     * @return The extracted number, or **Int.MAX_VALUE** if no number is found.
     */
    private fun extractNumber(name: String): Int {
        return name.filter { it.isDigit() }.toIntOrNull() ?: Int.MAX_VALUE
    }
}


