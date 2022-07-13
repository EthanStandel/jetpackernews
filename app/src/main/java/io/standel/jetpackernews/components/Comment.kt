package io.standel.jetpackernews.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.standel.jetpackernews.state.LocalStackNavState
import io.standel.jetpackernews.state.produceItemState

@Composable
fun Comment(storyId: Int, itemId: Int) {
    val comment = produceItemState(itemId)
    val author = comment?.by ?: "(anonymous)"
    val replyCount = comment?.kids?.size ?: 0
    val navState = LocalStackNavState.current!!

    Column(modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)) {
        Row(modifier = Modifier.padding(bottom = 8.dp)) {
            Column(modifier = Modifier.padding(end = 8.dp)) {
                Icon(Icons.Filled.AccountCircle, author)
            }
            Text(author, fontWeight = FontWeight.Bold)
        }
        // if we always try to use HtmlText here, sometimes the real comment fails to fully render
        if (comment?.text != null) HtmlText(comment.text) else Text("...")
        Row(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 8.dp, bottom = 8.dp),
            horizontalArrangement = Arrangement.End
        ) {
            Button(
                onClick = { navState.navigate("comments/$storyId/$itemId") },
                enabled = replyCount != 0
            ) { Text("Read $replyCount ${if (replyCount == 1) "reply" else "replies"}") }
        }
        Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))
    }
}