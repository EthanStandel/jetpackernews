package io.standel.jetpackernews.utils

import kotlinx.coroutines.*

suspend fun <T, R> Iterable<T>.mapAsync(transform: suspend (T) -> R): List<R> {
    return coroutineScope {
        val list = this@mapAsync.map { e ->
            async {
                transform(e)
            }
        }
        list.map { it.await() }
    }
}