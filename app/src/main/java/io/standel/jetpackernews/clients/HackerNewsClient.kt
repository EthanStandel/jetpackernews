package io.standel.jetpackernews.clients

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.standel.jetpackernews.models.Story
import com.google.gson.Gson
import io.standel.jetpackernews.utils.mapAsync
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope

private val gson = Gson()
private val client = HttpClient()
private val baseUrl =  "https://hacker-news.firebaseio.com/v0"

suspend fun fetchStoryIds(): List<Int> {
    val body: String = client.get("$baseUrl/topstories.json").body()
    return gson.fromJson(body, Array<Int>::class.java).toList()
}

suspend fun fetchStory(itemId: Int): Story {
    val body: String = client.get("$baseUrl/item/$itemId.json").body()
    return gson.fromJson(body, Story::class.java)
}
