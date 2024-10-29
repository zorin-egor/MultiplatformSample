package com.sample.app.core.ui.widgets

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.sample.app.core.ui.icon.AppIcons
import com.sample.app.core.ui.theme.AppTheme
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun RoundedPlaceholderWidget(
    header: String,
    image: ImageVector,
    modifier: Modifier = Modifier,
    title: String? = null,
    cornerRadius: Dp = 16.dp,
    imageContentDescription: String? = null
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(cornerRadius, cornerRadius, cornerRadius, cornerRadius),
    ) {
        SimplePlaceholderContent(
            header = header,
            title = title,
            image = image,
            imageContentDescription = imageContentDescription
        )
    }
}

@Preview
@Composable
fun RoundedPlaceholderWidgetPreview() {
    AppTheme {
        RoundedPlaceholderWidget(
            header = "Header",
            title = "Title",
            image = AppIcons.Search,
            imageContentDescription = "contentDesc"
        )
    }
}
