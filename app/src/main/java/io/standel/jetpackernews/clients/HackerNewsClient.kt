package io.standel.jetpackernews.clients

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.standel.jetpackernews.models.StoryOrComment
import com.google.gson.Gson

private val gson = Gson()
private val client = HttpClient()
private val baseUrl =  "https://hacker-news.firebaseio.com/v0"

suspend fun fetchStoryIds(queryType: String): List<Int> {
    val body: String = client.get(baseUrl + queryType).body()
    return gson.fromJson(body, Array<Int>::class.java).toList()
}

suspend fun fetchItem(itemId: Int): StoryOrComment {
    val body: String = client.get("$baseUrl/item/$itemId.json").body()
    return gson.fromJson(body, StoryOrComment::class.java)
}
