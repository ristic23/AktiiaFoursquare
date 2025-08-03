package com.aktiia.core.presentation.designsystem

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle

fun buildPrefixedText(
    prefix: String,
    value: String,
    textColor: Color
): AnnotatedString {
    val prefixColor = textColor.copy(alpha = 0.8f)

    return buildAnnotatedString {
        withStyle(style = SpanStyle(color = prefixColor)) {
            append("$prefix ")
        }
        withStyle(style = SpanStyle(color = textColor, fontWeight = FontWeight.Bold)) {
            append(value)
        }
    }
}