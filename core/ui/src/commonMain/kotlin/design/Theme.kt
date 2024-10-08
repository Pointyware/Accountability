/*
 * Copyright (c) 2024 Pointyware. Use of this software is governed by the GPL-3.0 license.
 */

package org.pointyware.accountability.core.ui.design

/**
 * Combines primitives to create a Placeholder theme
 */

import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import kotlinx.datetime.Instant
import org.jetbrains.compose.ui.tooling.preview.Preview

interface XPDateFormatter {
    fun format(date: Instant): String
}
val SimpleDateFormatter = object : XPDateFormatter {
    override fun format(date: Instant): String {
        return date.toString()
    }
}
val DateFormat = compositionLocalOf<XPDateFormatter> { throw IllegalStateException("DateFormat not provided") }

/**
 * Extends the Material3 Theme with an [XPDateFormatter].
 */
@Composable
fun PlaceholderTheme(
    isDark: Boolean = false,
    content: @Composable ()->Unit,
) {
    CompositionLocalProvider(
        DateFormat provides SimpleDateFormatter
    ) {
        MaterialTheme(
            colorScheme = if (isDark) darkColors else lightColors,
            shapes = shapes,
            typography = typography,
            content = content
        )
    }
}

@Preview
@Composable
fun PlaceholderThemePreview() {
    PlaceholderTheme {
        Surface {
            Text("ooh, a button")
            Button(onClick = { println("Click") }) {
                Text("Click me!")
            }
        }
    }
}
