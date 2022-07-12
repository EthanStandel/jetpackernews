package io.standel.jetpackernews.state

import androidx.compose.runtime.compositionLocalOf
import androidx.navigation.NavHostController

val LocalStackNavState = compositionLocalOf<NavHostController?>{ null }