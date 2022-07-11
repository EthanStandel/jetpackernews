package io.standel.jetpackernews

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import io.standel.jetpackernews.components.Layout
import io.standel.jetpackernews.ui.theme.JetpackernewsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetpackernewsTheme {
                Layout()
            }
        }
    }
}
