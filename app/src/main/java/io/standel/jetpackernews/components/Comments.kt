package io.standel.jetpackernews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import io.standel.jetpackernews.state.CommentRefreshViewModel
import io.standel.jetpackernews.state.CommentRefreshViewModelFactory

@Composable
fun Comments(storyId: Int, itemId: Int) {
    val commentRefresh: CommentRefreshViewModel = viewModel(
        factory = CommentRefreshViewModelFactory(itemId)
    )
    val commentGroupParent = commentRefresh.commentGroupParent.collectAsState().value
    val commentsRefreshing by commentRefresh.isRefreshingState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (commentGroupParent == null) {
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
        } else if ((commentGroupParent!!.kids ?: listOf()).isEmpty()) {
            Column(
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) { Text("No comments", modifier = Modifier) }
            }
        } else {
            SwipeRefresh(
                state = rememberSwipeRefreshState(commentsRefreshing),
                onRefresh = { commentRefresh.refresh() },
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        backgroundColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                }
            ) {
                LazyColumn {
                    item { StoryCard(storyId, false) }
                    item {
                        if (storyId != itemId) {
                            Column {
                                Row(modifier = Modifier.padding(8.dp)) {
                                    Text("Replies to")
                                    val author = commentGroupParent.by ?: "(anonymous)"
                                    Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp)) {
                                        Icon(Icons.Filled.AccountCircle, author)
                                    }
                                    Text(author, fontWeight = FontWeight.Bold)
                                }
                                Divider(modifier = Modifier.padding(bottom = 8.dp))
                            }
                        }
                    }
                    items(commentGroupParent.kids!!) { Comment(storyId, it) }
                }
            }
        }
    }
}