package io.standel.jetpackernews.utils

import androidx.compose.ui.graphics.Color
import kotlin.math.abs
import kotlin.math.pow

/**
 * Why does this function look like pure suffering?
 * Because it is. Legacy Android colors are 8 digit
 * hex value ints (ARGB, NOT RGBA for some god forsaken
 * reason)... but 8 digit hex value in practice OVERFLOW
 * THE 4 BYTE LIMIT and BECOME NEGATIVE... unless they're
 * HARDCODED, in which case they're down-converted from
 * long and they maintain their sign for some reason
 *
 * So if you're wondering about line 20, the answer is yes.
 * It does need to be a long, calculated the  absolute value
 * of the sum(even though the long shouldn't be overflowing),
 * and then reconverted back to an int. Not a single one of
 * these items can be removed to reliably get the same result.
 */
fun Color.toLegacyColorInt() = abs(
    + (alpha * 255 * 256f.pow(3)).toLong()
    + (red * 255 * 256f.pow(2)).toInt()
    + (green * 255 * 256).toInt()
    + (blue * 255).toInt()
).toInt()
