package io.standel.jetpackernews.models

import kotlinx.serialization.Serializable

// https://hackernews.api-docs.io/v0/items/story
data class Story(
    val id: Int,
    val deleted: Boolean,
    val type: String,
    val by: String,
    val time: Long,
    val dead: Boolean,
    val kids: List<Int>?,
    val descendants: Int,
    val score: Int,
    val title: String,
    val url: String?
)