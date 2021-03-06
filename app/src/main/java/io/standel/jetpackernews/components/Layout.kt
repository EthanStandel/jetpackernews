package io.standel.jetpackernews.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import io.standel.jetpackernews.state.LocalStackNavState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Composable
fun Layout() {
    val topBarScrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }
    val stackNavController = rememberAnimatedNavController()

    CompositionLocalProvider(LocalStackNavState provides stackNavController) {
        Scaffold(
            modifier = Modifier.nestedScroll(topBarScrollBehavior.nestedScrollConnection),
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Column {
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
                    StackNav()
                }
            }
        }
    }
}
