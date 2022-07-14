package io.standel.jetpackernews.state

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.standel.jetpackernews.models.StoryOrComment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class CommentRefreshViewModelFactory(
    private val itemId: Int,
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CommentRefreshViewModel::class.java)) {
            return CommentRefreshViewModel(itemId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class CommentRefreshViewModel(val itemId: Int) : ViewModel() {
    val commentGroupParent = MutableStateFlow<StoryOrComment?>(null)
    val isRefreshingState = MutableStateFlow(false)

    init { viewModelScope.launch { updateCommentGroupParent() } }

    private suspend fun updateCommentGroupParent() {
        commentGroupParent.emit(fetchItemState(itemId))
    }

    fun refresh() = viewModelScope.launch {
        isRefreshingState.emit(true)
        commentGroupParent.emit(null)
        itemStore.clear()
        updateCommentGroupParent()
        isRefreshingState.emit(false)
    }
}