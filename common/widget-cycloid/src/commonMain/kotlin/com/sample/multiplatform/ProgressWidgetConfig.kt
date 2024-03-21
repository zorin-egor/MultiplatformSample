package com.sample.multiplatform

import com.sample.multiplatform.models.ShapeModel

data class ProgressWidgetConfig(
    val shapeType: ShapeModel,
    val fromProgress: Int = 0,
    val toProgress: Int = 100
) {

    val key: String = buildString {
        append(shapeType::class.simpleName).append("_")
        append(toProgress).append("_")
    }

}
