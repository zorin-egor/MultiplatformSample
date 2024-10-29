package com.sample.app.core.ui.ext

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withLink


fun getHyperLink(url: String?, title: String? = null): AnnotatedString? {
    url?.takeIf { it.isNotEmpty() } ?: return null
    return buildAnnotatedString {
        withLink(link = LinkAnnotation.Url(
            url = url,
            styles = TextLinkStyles(style = SpanStyle(color = Color.Blue))
        )) {
            append(title ?: url)
        }
    }
}

fun getEmailLink(email: String?, title: String? = null): AnnotatedString? {
    email ?: return null
    return getHyperLink("mailto:$email", title)
}