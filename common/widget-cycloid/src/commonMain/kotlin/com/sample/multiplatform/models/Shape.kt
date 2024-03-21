package com.sample.multiplatform.models

import androidx.compose.ui.graphics.drawscope.DrawScope


internal interface Draw {

    fun draw(draw: DrawScope)

}

internal interface Shape : Draw {

    fun setSize(width: Float, height: Float)

    fun setProgress(value: Int)

}