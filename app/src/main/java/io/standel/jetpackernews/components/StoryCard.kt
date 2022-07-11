package io.standel.jetpackernews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import io.standel.jetpackernews.clients.fetchStory
import io.standel.jetpackernews.models.Story

val storyStore = mutableMapOf<Int, Story>()

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
            Text(text = story?.title ?: "...")
            Row (
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Column(modifier = Modifier.weight(1f)) {}
                Button(
                    onClick = { uriHandler.openUri(story?.url ?: "") },
                    enabled = story != null
                ) {
                    Text(text = "Open story")
                }
            }
        }
    }
}
