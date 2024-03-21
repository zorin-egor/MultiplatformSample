package com.sample.multiplatform.models.cycloid

import androidx.compose.ui.unit.dp
import com.sample.multiplatform.models.ShapeModel

sealed class CycloidModel(
    val colors: CycloidColors,
    val x1: Float, val y1: Float,
    val x2: Float, val y2: Float,
    val lineWidth: Float = 7.dp.value,
    val particleRadius: Float = 15.dp.value,
) : ShapeModel.Cycloid {
    class One(colors: CycloidColors, lineWidth: Float, particleRadius: Float) :
        CycloidModel(colors, 1.286000f, 4.242000f, 1.286000f, 4.242000f, lineWidth, particleRadius)
    class Two(colors: CycloidColors, lineWidth: Float, particleRadius: Float) :
        CycloidModel(colors, 4.283176f, 3.518178f, 3.683177f, 3.668177f, lineWidth, particleRadius)
    class Three(colors: CycloidColors, lineWidth: Float, particleRadius: Float) :
        CycloidModel(colors, 2.150000f, 2.299000f, 2.150000f, 2.299000f, lineWidth, particleRadius)
    class Four(colors: CycloidColors, lineWidth: Float, particleRadius: Float) :
        CycloidModel(colors, 3.216887f, 4.066366f, 3.216887f, 4.066366f, lineWidth, particleRadius)
    class Five(colors: CycloidColors, lineWidth: Float, particleRadius: Float) :
        CycloidModel(colors, 1.745000f, 1.575000f, 1.745000f, 1.575000f, lineWidth, particleRadius)
}