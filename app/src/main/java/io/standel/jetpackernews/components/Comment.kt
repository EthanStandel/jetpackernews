package io.standel.jetpackernews.components

import androidx.compose.runtime.Composable
import io.standel.jetpackernews.state.produceItemState

@Composable
fun Comment(itemId: Int) {
    val comment = produceItemState(itemId)

    HtmlText(text = comment?.text ?: "...")
}