package com.sample.app.widget.cycloid

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.sample.app.widget.cycloid.models.cycloid.Cycloid
import com.sample.app.widget.cycloid.models.cycloid.CycloidModel

@Composable
fun ProgressWidget(
    progress: State<Int>,
    widgetConfig: ProgressWidgetConfig,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    println("ProgressWidget() - dp: ${1.dp}; ${1.dp.value}")

    val shape = remember(key1 = widgetConfig.key) {
        println("ProgressWidget() - remember - cycloid")
        when(val type = widgetConfig.shapeType) {
            is CycloidModel -> {
                Cycloid(
                    model = type,
                    fromProgress = widgetConfig.fromProgress,
                    toProgress = widgetConfig.toProgress,
                    isDynamicColor = widgetConfig.isDynamic,
                    isDynamicShape = widgetConfig.isDynamic
                )
            }
            else -> throw IllegalStateException("No shape type")
        }
    }

    println("ProgressWidget() - before canvas")

    val invalidate: State<Boolean>? = if (widgetConfig.isDynamic) {
        produceState(initialValue = true) {
            while(true) {
                withFrameNanos {
                    value = value.not()
                }
            }
        }
    } else {
        null
    }

    Canvas(modifier) {
        invalidate?.value
        val progressValue = progress.value

        println("ProgressWidget() - canvas: progress: $progressValue, width = ${size.width}, height = ${size.height}")

        shape.setSize(width = size.width, height = size.height)
        shape.setProgress(progress.value)
        shape.draw(draw = this)
    }
}