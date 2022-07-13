package io.standel.jetpackernews.components

import android.widget.TextView
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import io.standel.jetpackernews.utils.toLegacyColorInt

@Composable
fun HtmlText(text: String) {
    val textColor = MaterialTheme.colorScheme.onBackground

    AndroidView(
        factory = { context ->
            TextView(context).apply {
                setTextColor(textColor.toLegacyColorInt())
                setText(HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY))
            }
        },
        update = { it.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY) },
    )
}