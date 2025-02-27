package com.thatwaz.fetchlist.data

import com.thatwaz.fetchlist.data.model.FetchItem
import com.thatwaz.fetchlist.data.remote.FetchApiService
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

/**
 * Unit test for [FetchRepository.getFilteredSortedItems].
 *
 * This test verifies that:
 * - Items with blank or null names are **filtered out**.
 * - The remaining items are **sorted correctly**:
 *    1. By `listId` (ascending).
 *    2. By the number in the `name` field (numerically, not lexicographically).
 *
 *  Example sorting logic:
 *   - "Item 2" appears **before** "Item 12" (since 2 < 12).
 *   - Items from `listId = 1` appear **before** those from `listId = 2`.
 */
class FetchRepositoryTest {

    @Test
    fun `getFilteredSortedItems should filter and sort items correctly`() = runBlocking {
        val fakeApiService = object : FetchApiService {
            override suspend fun getItems(): List<FetchItem> {
                return listOf(
                    FetchItem(1, 2, "Item 10"),
                    FetchItem(2, 1, "Item 2"),
                    FetchItem(3, 1, "Item 12"),
                    FetchItem(4, 1, ""), // Blank name (should be filtered)
                    FetchItem(5, 1, null) // Null name (should be filtered)
                )
            }
        }

        val repository = FetchRepository(fakeApiService)
        val result = repository.getFilteredSortedItems()

        assertEquals(3, result.size) // Should remove blank/null names

        // Check sorting (by listId first, then number in name)
        assertEquals("Item 2", result[0].name)   // List 1, Item 2
        assertEquals("Item 12", result[1].name)  // List 1, Item 12
        assertEquals("Item 10", result[2].name)  // List 2, Item 10
    }

    @Test
    fun `getFilteredSortedItems should return empty list when all names are blank or null`() = runBlocking {
        val fakeApiService = object : FetchApiService {
            override suspend fun getItems(): List<FetchItem> {
                return listOf(
                    FetchItem(1, 1, ""),
                    FetchItem(2, 2, null),
                    FetchItem(3, 3, "   ") // Only spaces (should be filtered)
                )
            }
        }

        val repository = FetchRepository(fakeApiService)
        val result = repository.getFilteredSortedItems()

        assertEquals(0, result.size) // Should return an empty list
    }
}


