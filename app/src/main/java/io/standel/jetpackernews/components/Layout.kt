package io.standel.jetpackernews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Layout() {
    val topBarScrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }
    val (navState, setNavState) = remember { mutableStateOf(bottomNavItems[0]) }

    Scaffold(
        modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            "JetpackerNews",
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    },
                    colors = TopAppBarDefaults.smallTopAppBarColors(
                        MaterialTheme.colorScheme.primary
                    ),
                    scrollBehavior = topBarScrollBehavior
                )
                Column(modifier = Modifier.weight(1F)) {
                    Stories(navState)
                }
                BottomNav(navState, setNavState)
            }
        }
    }
}
