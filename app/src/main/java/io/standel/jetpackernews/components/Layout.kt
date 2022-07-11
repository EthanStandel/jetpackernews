package io.standel.jetpackernews.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable

import androidx.compose.ui.Modifier

@Composable
fun Layout() {
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
            )
            Column(modifier = Modifier.weight(1F)) {
                Stories()
            }
            BottomAppBar {

            }
        }
    }
}