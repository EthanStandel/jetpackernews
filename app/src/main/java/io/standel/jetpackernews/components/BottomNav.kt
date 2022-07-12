package io.standel.jetpackernews.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContactSupport
import androidx.compose.material.icons.filled.NewReleases
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import io.standel.jetpackernews.models.NavItem
import io.standel.jetpackernews.models.QueryType

val bottomNavItems = listOf(
    NavItem(Icons.Filled.Star, "Top", QueryType.Top),
    NavItem(Icons.Filled.NewReleases, "New", QueryType.New),
    NavItem(Icons.Filled.ContactSupport, "Ask", QueryType.Ask),
    NavItem(Icons.Filled.Work, "Jobs", QueryType.Job)
)

@Composable
fun BottomNav(selectedItem: NavItem, setSelectedItem: (NavItem) -> Unit) {
    NavigationBar {
        bottomNavItems.forEach { navItem ->
            NavigationBarItem(
                icon = { Icon(navItem.icon, contentDescription = navItem.text)},
                label = { Text(navItem.text) },
                selected = selectedItem === navItem,
                onClick = { setSelectedItem(navItem) }
            )
        }
    }
}