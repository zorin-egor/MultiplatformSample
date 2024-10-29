package com.sample.app.widget.cycloid

import com.sample.app.widget.cycloid.models.ShapeModel

data class ProgressWidgetConfig(
    val shapeType: ShapeModel,
    val isDynamic: Boolean = true,
    val fromProgress: Int = 0,
    val toProgress: Int = 100
) {

    val key: String = buildString {
        append(shapeType::class.simpleName).append("_")
        append(toProgress).append("_")
    }

}
