package io.standel.jetpackernews.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.standel.jetpackernews.state.produceItemState

@Composable
fun Comment(itemId: Int) {
    val comment = produceItemState(itemId)

    if (comment?.text != null) HtmlText(comment.text) else Text("...")
}