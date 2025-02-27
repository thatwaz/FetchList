package com.thatwaz.fetchlist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.thatwaz.fetchlist.data.FetchRepository
import com.thatwaz.fetchlist.data.model.FetchItem
import com.thatwaz.fetchlist.viewmodel.FetchViewModel
import com.thatwaz.fetchlist.viewmodel.FetchViewModelFactory
import kotlinx.coroutines.delay


@Composable
fun FetchScreen(repository: FetchRepository) {
    val viewModel: FetchViewModel = viewModel(factory = FetchViewModelFactory(repository))
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    when (uiState) {
        is FetchViewModel.UiState.Loading -> {
            LoadingScreen()
        }
        is FetchViewModel.UiState.Error -> {
            ErrorScreen(
                message = (uiState as FetchViewModel.UiState.Error).message,
                onRetry = { viewModel.fetchItems() } // Calls fetchItems() on retry
            )
        }
        is FetchViewModel.UiState.Success -> {
            val items = (uiState as FetchViewModel.UiState.Success).items
            DisplayList(items, listState)
        }
    }
}




@Composable
fun DisplayList(items: List<FetchItem>, listState: LazyListState) {
    val groupedItems = items.groupBy { it.listId }.toSortedMap()
    val firstListId = groupedItems.keys.firstOrNull()

    var expandedSections by remember { mutableStateOf(setOfNotNull(firstListId)) }
    var isUIReady by remember { mutableStateOf(false) }
    var showFullList by remember { mutableStateOf(false) }

    LaunchedEffect(items) {
        if (firstListId != null) {
            delay(150)
            expandedSections = setOf(firstListId)
            isUIReady = true
        }
    }

    if (isUIReady) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            groupedItems.forEach { (listId, groupedItems) ->
                item {
                    ExpandableSectionHeader(
                        listId = listId,
                        isExpanded = expandedSections.contains(listId),
                        onToggle = {
                            expandedSections = if (expandedSections.contains(listId)) {
                                expandedSections - listId
                            } else {
                                expandedSections + listId
                            }
                        }
                    )
                }

                if (expandedSections.contains(listId)) {
                    val isFirstList = listId == firstListId
                    val visibleItems = if (isFirstList && !showFullList) groupedItems.take(5) else groupedItems

                    items(visibleItems.sortedBy { it.name?.lowercase() }) { item ->
                        FetchItemCard(item)
                    }

                    if (isFirstList && groupedItems.size > 5 && !showFullList) {
                        item {
                            ShowMoreButton { showFullList = true }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(message: String, onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp), // Adds some spacing from edges
        contentAlignment = Alignment.Center
    ) {
        Card(
            elevation = CardDefaults.cardElevation(8.dp), // Gives a slight shadow
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.padding(16.dp) // Adds some space around the card
        ) {
            Column(
                modifier = Modifier
                    .padding(24.dp) // Adds padding inside the card
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "⚠️ $message",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onRetry() }) {
                    Text(text = "Retry")
                }
            }
        }
    }
}

@Composable
fun ShowMoreButton(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Show More",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun ExpandableSectionHeader(listId: Int, isExpanded: Boolean, onToggle: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onToggle() },
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "📂 List ID: $listId",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            Icon(
                imageVector = if (isExpanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                contentDescription = "Expand or Collapse",
                tint = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun FetchItemCard(item: FetchItem) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        shape = MaterialTheme.shapes.medium,
        elevation = CardDefaults.cardElevation(6.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = item.name ?: "Unnamed",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = "🆔 ${item.id}",
                style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}






