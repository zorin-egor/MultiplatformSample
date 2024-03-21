package com.sample.multiplatform.image_loader

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import com.seiko.imageloader.LocalImageLoader
import com.seiko.imageloader.model.ImageRequest
import com.seiko.imageloader.option.toScale
import com.seiko.imageloader.rememberImageAction
import com.seiko.imageloader.rememberImageActionPainter

@Composable
fun loadImage(
    url: String?,
    scale: ContentScale = ContentScale.Fit,
    filter: FilterQuality = DrawScope.DefaultFilterQuality
): Painter {
    val request = remember(url) {
        ImageRequest {
            data(url)
            scale(scale.toScale())
        }
    }
    val imageAction by rememberImageAction(request, LocalImageLoader.current)
    return rememberImageActionPainter(imageAction, filter)
}