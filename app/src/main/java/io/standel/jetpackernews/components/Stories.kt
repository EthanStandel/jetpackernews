package io.standel.jetpackernews.components

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.standel.jetpackernews.clients.fetchStoryIds
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun Stories() {
    val viewModel: StoryFetching = viewModel()
    val isRefreshing by viewModel.isRefreshingState.collectAsState()
    val storyIds by viewModel.storyIdsState.collectAsState()

    SwipeRefresh(
        state = rememberSwipeRefreshState(isRefreshing),
        onRefresh = { viewModel.refresh() },
        indicator = { state, trigger ->
            SwipeRefreshIndicator(
                state = state,
                refreshTriggerDistance = trigger,
                backgroundColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        }
    ) { LazyColumn { items(storyIds) { StoryCard(it) } } }
}

class StoryFetching : ViewModel() {
    val storyIdsState = MutableStateFlow<List<Int>>(listOf())
    val isRefreshingState = MutableStateFlow(false)

    init { viewModelScope.launch { updateStoryIds() } }

    private suspend fun updateStoryIds() {
        storyIdsState.emit(fetchStoryIds())
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshingState.emit(true)
            updateStoryIds()
            isRefreshingState.emit(false)
        }
    }
}
