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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.unit.dp
import io.standel.jetpackernews.models.Story

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryCard(story: Story?) {
    val uriHandler = LocalUriHandler.current

    ElevatedCard(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 8.dp, 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            if (story != null) {
                Text(text = story.title)
                Row (
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Column(modifier = Modifier.weight(1f)) {}
                    Button(onClick = { uriHandler.openUri(story.url) }) {
                        Text(text = "Open story")
                    }
                }
            }
        }
    }
}
