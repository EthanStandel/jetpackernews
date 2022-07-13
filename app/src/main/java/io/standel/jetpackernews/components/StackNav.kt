package io.standel.jetpackernews.components

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import io.standel.jetpackernews.state.LocalStackNavState
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
@Composable
fun StackNav() {
    AnimatedNavHost(LocalStackNavState.current!!, "stories") {
        composable("stories") { Stories() }
        composable(
            "comments/{storyId}/{itemId}",
            arguments = listOf(
                navArgument("storyId") { type = NavType.IntType },
                navArgument("itemId") { type = NavType.IntType }
            ),
            enterTransition = { slideIntoContainer(
                AnimatedContentScope.SlideDirection.Left,
                animationSpec = tween(500)
            ) },
            exitTransition = { slideOutOfContainer(
                AnimatedContentScope.SlideDirection.Right,
                animationSpec = tween(500)
            ) },
        ) { Comments(
            it.arguments?.getInt("storyId")!!,
            it.arguments?.getInt("itemId")!!
        ) }
    }
}