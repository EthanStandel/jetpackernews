package io.standel.jetpackernews.state

import androidx.compose.runtime.Composable
import androidx.compose.runtime.produceState
import io.standel.jetpackernews.clients.fetchItem
import io.standel.jetpackernews.models.StoryOrComment

val itemStore = mutableMapOf<Int, StoryOrComment>()

@Composable
fun produceItemState(id: Int): StoryOrComment? {
    return produceState<StoryOrComment?>(null, id) {
        if (!itemStore.containsKey(id)) {
            itemStore[id] = fetchItem(id)
        }
        value = itemStore[id]
    }.value
}