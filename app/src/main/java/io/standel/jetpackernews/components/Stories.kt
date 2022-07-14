package io.standel.jetpackernews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.standel.jetpackernews.state.StoryRefreshViewModel

@Composable
fun Stories() {
    val storyRefreshing: StoryRefreshViewModel = viewModel()
    val isRefreshing by storyRefreshing.isRefreshingState.collectAsState()
    val storyIds by storyRefreshing.storyIdsState.collectAsState()
    val bottomNavState by storyRefreshing.bottomNavState.collectAsState()
    val scrollState = rememberLazyListState()

    // Reset scroll state when other bottom nav-item (queryType) is selected
    LaunchedEffect(bottomNavState) {
        scrollState.scrollToItem(0)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = { storyRefreshing.refresh() },
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
                } else LazyColumn(state = scrollState) { items(storyIds) { StoryCard(it) } }
            }
        }
        BottomNav(storyRefreshing)
    }
}
