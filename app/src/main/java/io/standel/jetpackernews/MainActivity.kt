package io.standel.jetpackernews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.ui.Modifier
import io.standel.jetpackernews.components.Stories
import io.standel.jetpackernews.ui.theme.JetpackernewsTheme
import com.google.accompanist.swiperefresh.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackernewsTheme {
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
        }
    }
}
