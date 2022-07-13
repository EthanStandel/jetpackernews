package io.standel.jetpackernews.models

// https://hackernews.api-docs.io/v0/items/story
// https://hackernews.api-docs.io/v0/items/comment
// Don't judge me for how weird the idea of this model is...
data class StoryOrComment(
    val id: Int,
    val deleted: Boolean,
    val type: String,
    val by: String?,
    val time: Long,
    val dead: Boolean,
    val kids: List<Int>?,
    val title: String,

    // story specific
    val descendants: Int,
    val score: Int,
    val url: String?,

    // comment specific
    val parent: Int?,
    val text: String?
)