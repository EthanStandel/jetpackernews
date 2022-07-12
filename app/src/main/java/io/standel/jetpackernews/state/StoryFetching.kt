package io.standel.jetpackernews.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.standel.jetpackernews.clients.fetchStoryIds
import io.standel.jetpackernews.components.bottomNavItems
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoryFetching : ViewModel() {
    val storyIdsState = MutableStateFlow<List<Int>>(listOf())
    val isRefreshingState = MutableStateFlow(false)
    // TODO - find a way to not have to do this extra state instance
    val navState = MutableStateFlow(bottomNavItems[0])

    init { viewModelScope.launch { updateStoryIds() } }

    suspend fun updateStoryIds() {
        storyIdsState.emit(fetchStoryIds(navState.value.queryType))
    }

    fun refresh() {
        viewModelScope.launch {
            isRefreshingState.emit(true)
            storyIdsState.emit(listOf())
            storyStore.clear()
            updateStoryIds()
            isRefreshingState.emit(false)
        }
    }
}