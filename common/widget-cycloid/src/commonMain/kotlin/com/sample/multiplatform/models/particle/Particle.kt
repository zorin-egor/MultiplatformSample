package com.sample.multiplatform.models.particle


import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sample.multiplatform.models.Draw

internal data class Particle(
    var x: Float,
    var y: Float,
    var radius: Float,
    var delta: Float,
    var color: Color = Color.Black
) : Draw {

    override fun draw(draw: DrawScope) {
        draw.drawCircle(color = color, radius = radius, center = Offset(x, y))
    }

}