package io.standel.jetpackernews.state

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.standel.jetpackernews.clients.fetchStoryIds
import io.standel.jetpackernews.components.bottomNavItems
import io.standel.jetpackernews.models.NavItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class StoryRefreshViewModel : ViewModel() {
    val storyIdsState = MutableStateFlow<List<Int>>(listOf())
    val isRefreshingState = MutableStateFlow(false)
    val bottomNavState = MutableStateFlow(bottomNavItems[0])

    init {
        viewModelScope.launch { updateStoryIds() }
    }

    private suspend fun updateStoryIds() {
        if (queryStore[bottomNavState.value.queryType] == null) {
            val storyIds = fetchStoryIds(bottomNavState.value.queryType)
            queryStore[bottomNavState.value.queryType] = storyIds
            storyIdsState.emit(storyIds)
        } else {
            storyIdsState.emit(queryStore[bottomNavState.value.queryType]!!)
        }
    }

    fun changeBottomNavState(navItem: NavItem) = viewModelScope.launch {
        bottomNavState.emit(navItem)
        storyIdsState.emit(listOf())
        updateStoryIds()
    }


    fun refresh() = viewModelScope.launch {
        isRefreshingState.emit(true)
        storyIdsState.emit(listOf())
        itemStore.clear()
        queryStore.remove(bottomNavState.value.queryType)
        updateStoryIds()
        isRefreshingState.emit(false)
    }

}
