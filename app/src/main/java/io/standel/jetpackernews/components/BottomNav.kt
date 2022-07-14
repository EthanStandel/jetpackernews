package io.standel.jetpackernews.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import io.standel.jetpackernews.models.NavItem
import io.standel.jetpackernews.models.QueryType
import io.standel.jetpackernews.state.StoryRefreshViewModel

val bottomNavItems = listOf(
    NavItem(Icons.Filled.Star, "Top", QueryType.Top),
    NavItem(Icons.Filled.NewReleases, "New", QueryType.New),
    NavItem(Icons.Filled.DesignServices, "Show", QueryType.Show),
    NavItem(Icons.Filled.ContactSupport, "Ask", QueryType.Ask),
    NavItem(Icons.Filled.Work, "Jobs", QueryType.Job)
)

@Composable
fun BottomNav(storyRefreshing: StoryRefreshViewModel) {
    val bottomNavState = storyRefreshing.bottomNavState.collectAsState().value

    NavigationBar {
        bottomNavItems.forEach { navItem ->
            NavigationBarItem(
                icon = { Icon(navItem.icon, contentDescription = navItem.text)},
                label = { Text(navItem.text) },
                selected = bottomNavState === navItem,
                onClick = { storyRefreshing.changeBottomNavState(navItem) }
            )
        }
    }
}