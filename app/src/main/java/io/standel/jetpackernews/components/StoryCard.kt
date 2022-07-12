package io.standel.jetpackernews.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.standel.jetpackernews.clients.fetchStory
import io.standel.jetpackernews.models.Story
import io.standel.jetpackernews.state.storyStore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryCard(id: Int) {
    val uriHandler = LocalUriHandler.current
    val story = produceState<Story?>(null, id) {
        if (!storyStore.containsKey(id)) {
            storyStore[id] = fetchStory(id)
        }
        value = storyStore[id]
    }.value

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = story?.title ?: "...",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            val score = if (story?.score != null) "${story.score} points " else ""
            val author = if (story?.by != null) "by ${story.by} " else ""
            val comments = if (story?.kids?.size != null) "| ${story.kids.size} comments " else ""
            Text(
                text = score + author + comments,
                fontSize = 12.sp,
                modifier = Modifier.padding(top = 8.dp)
            )
            Row (
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {}
                Button(
                    onClick = {

                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    enabled = story != null
                ) {
                    Text(text = "View comments")
                }
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {
                        uriHandler.openUri(
                            story?.url
                                ?: "https://news.ycombinator.com/item?id=${story?.id}"
                        )
                    },
                    enabled = story != null
                ) {
                Text(text = "View story")
            }
            }
        }
    }
}
