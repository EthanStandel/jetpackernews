package io.standel.jetpackernews.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavOptions
import io.standel.jetpackernews.state.LocalStackNavState
import io.standel.jetpackernews.state.produceItemState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoryCard(id: Int, showCommentLink: Boolean = true) {
    val stackNav = LocalStackNavState.current!!
    val uriHandler = LocalUriHandler.current
    val story = produceItemState(id)

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
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (showCommentLink) Button(
                    onClick = {
                        stackNav.navigate(
                            "comments/${story?.id}/${story?.id}",
                            NavOptions.Builder().setRestoreState(true).build()
                        )
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    enabled = story != null
                ) { Text(text = "View comments") }
                Button(
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = {
                        uriHandler.openUri(
                            story?.url
                                ?: "https://news.ycombinator.com/item?id=${story?.id}"
                        )
                    },
                    enabled = story != null
                ) { Text(text = "Read story") }
            }
        }
    }
}
