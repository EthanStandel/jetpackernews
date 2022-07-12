package io.standel.jetpackernews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.viewModelFactory
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.standel.jetpackernews.clients.fetchStoryIds
import io.standel.jetpackernews.models.NavItem
import io.standel.jetpackernews.state.StoryFetching
import io.standel.jetpackernews.state.storyStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun Stories(navState: NavItem) {
    val viewModel: StoryFetching = viewModel()
    val isRefreshing by viewModel.isRefreshingState.collectAsState()
    val storyIds by viewModel.storyIdsState.collectAsState()

    LaunchedEffect(navState) {
        viewModel.navState.emit(navState)
        viewModel.storyIdsState.emit(listOf())
        viewModel.updateStoryIds()
    }

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
    ) {
        if (storyIds.isEmpty()) {
            Column(modifier = Modifier.fillMaxSize()) {
                Column(modifier = Modifier.weight(1f)) {}
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth().padding(16.dp))
                Column(modifier = Modifier.weight(1f)) {}
            }
        } else {
            LazyColumn { items(storyIds) { StoryCard(it) } }
        }
    }
}
