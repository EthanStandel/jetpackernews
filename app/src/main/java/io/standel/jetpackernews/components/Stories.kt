package io.standel.jetpackernews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewModelScope
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.standel.jetpackernews.clients.fetchStory
import io.standel.jetpackernews.clients.fetchStoryIds
import io.standel.jetpackernews.models.Story
import io.standel.jetpackernews.utils.mapAsync
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@Composable
fun Stories() {
    val viewModel: RefreshingView = viewModel()
    val isRefreshing by viewModel.isRefreshingState.collectAsState()
    val stories by viewModel.storiesState.collectAsState()

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
        Column(Modifier.verticalScroll(rememberScrollState())) {
            stories.forEach { StoryCard(it) }
        }
    }
}

class RefreshingView : ViewModel() {
    private val storyIdsState = MutableStateFlow<List<Int>>(listOf())
    val isRefreshingState = MutableStateFlow(false)
    val storiesState = MutableStateFlow<List<Story?>>(listOf())

    init { viewModelScope.launch { fetchFreshStories() } }

    private suspend fun getPageOfStories(page: Int = 0, pageSize: Int = 10) {
        val from = 0 + page * pageSize
        val to = pageSize + page * pageSize
        val storyIds = storyIdsState.value
        val storyIdSublist = if (from > storyIds.lastIndex) {
            listOf()
        } else {
            storyIds.subList(
                from,
                if (to > storyIds.lastIndex) storyIds.lastIndex else to
            )
        }
        storiesState.emit(storyIdSublist.map { null })
        storiesState.emit(storyIdSublist.mapAsync { fetchStory(it) })
        isRefreshingState.emit(false)
    }

    private suspend fun fetchFreshStories(pageSize: Int = 10) {
        storyIdsState.emit(fetchStoryIds())
        getPageOfStories(pageSize = pageSize)
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshingState.emit(true)
            fetchFreshStories()
        }
    }
}
