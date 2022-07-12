package io.standel.jetpackernews.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import io.standel.jetpackernews.state.LocalStackNavState

@Composable
fun StackNav() {
    NavHost(LocalStackNavState.current!!, "stories") {
        composable("stories") { Stories() }
        composable(
            "comments/{itemId}",
            arguments = listOf(
                navArgument("itemId") { type = NavType.IntType }
            )
        ) { Comments(it.arguments?.getInt("itemId")!!) }
    }
}