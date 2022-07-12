package io.standel.jetpackernews.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.standel.jetpackernews.state.StoryFetching

@Composable
fun Stories() {
    val viewModel: StoryFetching = viewModel()
    val isRefreshing by viewModel.isRefreshingState.collectAsState()
    val storyIds by viewModel.storyIdsState.collectAsState()
    val (navState, setNavState) = remember { mutableStateOf(bottomNavItems[0]) }

    LaunchedEffect(navState) {
        viewModel.navState.emit(navState)
        viewModel.storyIdsState.emit(listOf())
        viewModel.updateStoryIds()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.weight(1f)) {
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
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        LinearProgressIndicator(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        )
                    }
                } else LazyColumn { items(storyIds) { StoryCard(it) } }
            }
        }
        BottomNav(navState, setNavState)
    }
}
