package com.sample.app.feature.splash.models

import com.sample.app.widget.cycloid.models.cycloid.CycloidColors

data class SplashUiModel(
    val progress: Int,
    val toProgress: Int = 100,
    val cycloidColors: CycloidColors = CycloidColors()
)
