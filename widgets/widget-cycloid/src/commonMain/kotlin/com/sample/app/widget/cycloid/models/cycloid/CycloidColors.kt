package com.sample.app.widget.cycloid.models.cycloid

import androidx.compose.ui.graphics.Color
import com.sample.app.widget.cycloid.models.ShapeColors

data class CycloidColors(
    override val backgroundColor: Color = Color.White,
    override val defaultColor: Color = Color.Gray,
    override val progressColor: Color = Color.Green,
    val particleDefaultColor: Color = defaultColor,
    val particleProgressColor: Color = progressColor,
    val lineDefaultColor: Color = defaultColor,
    val lineProgressColor: Color = progressColor,
) : ShapeColors
