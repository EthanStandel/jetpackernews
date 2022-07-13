package io.standel.jetpackernews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.standel.jetpackernews.state.produceItemState

@Composable
fun Comments(storyId: Int, itemId: Int) {
    val commentGroupParent = produceItemState(itemId)

    Column(
        modifier = Modifier.fillMaxSize()
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
                    items(commentGroupParent.kids!!) { Comment(it) }
                }
            }
        }
    }
}