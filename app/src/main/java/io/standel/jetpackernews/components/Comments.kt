package io.standel.jetpackernews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.standel.jetpackernews.state.produceItemState

@Composable
fun Comments(storyId: Int, itemId: Int) {
    val commentGroupParent = produceItemState(itemId)

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
        } else if ((commentGroupParent.kids ?: listOf()).isEmpty()) {
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
            Column {
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