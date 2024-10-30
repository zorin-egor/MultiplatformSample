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
    progress: Int,
    widgetConfig: ProgressWidgetConfig,
    modifier: Modifier = Modifier.fillMaxSize()
) {
    println("ProgressWidget() - dp: ${1.dp}; ${1.dp.value}")

    val originShape = remember {
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

    println("ProgressWidget() - before canvas: ${originShape.hashCode()}")

    val drawState: State<Boolean>? = if (widgetConfig.isDynamic) {
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
        drawState?.value
//        println("ProgressWidget() - canvas: progress: $progressValue, width = ${size.width}, height = ${size.height}")
        originShape.setSize(width = size.width, height = size.height)
        originShape.setProgress(progress)
        originShape.draw(draw = this)
    }
}