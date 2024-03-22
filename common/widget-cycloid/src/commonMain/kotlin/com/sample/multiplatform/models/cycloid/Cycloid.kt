package com.sample.multiplatform.models.cycloid

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.sample.multiplatform.models.Point
import com.sample.multiplatform.models.Shape
import com.sample.multiplatform.models.particle.Particle
import kotlinx.datetime.Clock
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.min
import kotlin.math.sin

internal class Cycloid(
    private val model: CycloidModel,
    private val fromProgress: Int = 0,
    private val toProgress: Int = 100,
    private val isDynamicColor: Boolean = true,
    private val isDynamicShape: Boolean = false,
) : Shape {

    companion object {
        val TAG = Cycloid::class.simpleName
        private const val LINE_TIMER = 100
        private const val PARTICLES_DELTA = 0.4f
        private const val RADIUS_MAX = 0.30f
        private const val RADIUS_MIN = 0.20f
        private const val RADIUS_DELTA = 0.001f
        private const val DELTA_SPEED = 0.01f
        private const val PARTICLE_RADIUS_FACTOR = 1.1f
    }

    private val points = ArrayList<Particle>(toProgress)
    private var totalSpeed: Float = 0f
    private var deltaSpeed: Float = DELTA_SPEED
    private var totalRadius: Float = RADIUS_MAX
    private var deltaRadius: Float = RADIUS_DELTA
    private var timeProgress: Long = 0
    private var indexProgress: Int = fromProgress

    private var width: Float = 0.0f
    private var height: Float = 0.0f

    private val systemTime: Long
        get() = Clock.System.now().toEpochMilliseconds()
    private val isTimeProgress: Boolean
        get() = systemTime - timeProgress > LINE_TIMER

    private val sizeCoefficient: Float
        get() = width / height

    private val center: Point
        get() = Point(width / 2.0f, height / 2.0f)

    private val radius: Point
        get() = if (sizeCoefficient > 1.0) {
            Point(width * totalRadius / sizeCoefficient, height * totalRadius)
        } else {
            Point(width * totalRadius, height * totalRadius * sizeCoefficient)
        }

    private val particleColor: Color get() = model.colors.particleDefaultColor

    private val lineColor: Color get() = model.colors.lineDefaultColor

    private val particleProgressColor: Color get() = model.colors.particleProgressColor

    private val lineProgressColor: Color get() = model.colors.lineProgressColor


    private fun setParticles() {
        var delta = 0.0f

        (0 until toProgress).forEach { index ->
            points.add(Particle(
                x = 0.0f, y = 0.0f,
                radius = model.particleRadius,
                delta = delta,
                color = particleColor.addToRGBDynamic(
                    factor = index.toFloat() / toProgress,
                    isAlphaChannelAddOrSub = false
                )
            ).also(::setParticleXY))

            delta += PARTICLES_DELTA
        }
    }

    private fun setSpeed() {
        if (abs(totalSpeed - 2 * PI) < 0.001) totalSpeed = 0.0f
        totalSpeed += deltaSpeed
    }

    private fun setRadius() {
        if (isDynamicShape) {
            deltaRadius *= if (totalRadius > RADIUS_MAX || totalRadius < RADIUS_MIN) -1.0f else 1.0f
            totalRadius += deltaRadius
        }
    }

    private inline fun setParticleXY(particle: Particle) {
        particle.x = center.x + radius.x * (cos(particle.delta + totalSpeed) +
                cos(model.x1 * (particle.delta + totalSpeed)) / model.y1)
        particle.y = center.y + radius.y * (sin(particle.delta + totalSpeed) +
                sin(model.x2 * (particle.delta + totalSpeed)) / model.y2)
    }

    private fun setProgress() {
        if (isTimeProgress && indexProgress < points.lastIndex) {
            timeProgress = systemTime
            indexProgress++
        }
    }

    private fun drawShape(draw: DrawScope) {
        println("$TAG - draw() - $systemTime")

        // Background
        draw.drawRect(color = model.colors.backgroundColor)

        // Draw close line under first
        if (points.size > 2) {
            draw.drawLine(
                color = if (indexProgress >= points.lastIndex)
                    lineProgressColor.addToRGBDynamic(factor = 1.0f, isAlphaChannelAddOrSub = false) else
                        lineColor.addToRGBDynamic(factor = 1.0f),
                start = Offset(points.first().x, points.first().y),
                end = Offset(points.last().x, points.last().y),
                strokeWidth = model.lineWidth
            )
        }

        // Draw main particles and lines
        points.forEachIndexed { index, particle ->
            setParticleXY(particle)

            val factor = index.toFloat() / toProgress
            val radius = model.particleRadius
            var particleColor = particleColor.addToRGBDynamic(factor = factor)
            var lineColor = lineColor.addToRGBDynamic(factor = factor)

            if (index in 0..indexProgress) {
                particleColor = particleProgressColor.addToRGBDynamic(
                    factor = factor * 0.5f,
                    isAlphaChannelAddOrSub = false
                )
                lineColor = lineProgressColor.addToRGBDynamic(
                    factor = factor * 0.5f,
                    isAlphaChannelAddOrSub = false
                )
            }

            if (index < points.lastIndex) {
                val next = points[index + 1]
                draw.drawLine(
                    color = lineColor,
                    start = Offset(next.x, next.y),
                    end = Offset(particle.x, particle.y),
                    strokeWidth = model.lineWidth
                )
            }

            particle.color = particleColor
            particle.radius = radius
            particle.draw(draw)
        }
    }

    override fun setSize(width: Float, height: Float) {
        if (abs(width) < 0.00000001f || abs(height) < 0.00000001f ||
            (this.width == width && this.height == height)) {
            println("$TAG - onSize($width, $height) - return")
            return
        }

        this.width = width
        this.height = height
        setParticles()
    }

    override fun setProgress(value: Int) {
        if (value == indexProgress || value !in 0..toProgress) {
            return
        }
        indexProgress = value
    }

    override fun draw(draw: DrawScope) {
        setSpeed()
        setRadius()
        drawShape(draw)
        setProgress()
    }

    private inline fun Color.addToRGB(factor: Float, isAlphaChannelAddOrSub: Boolean? = null): Color {
        val newRed = min(max(red + (1.0f - red) * factor, 0.0f), 1.0f)
        val newGreen = min(max(green + (1.0f - green) * factor, 0.0f), 1.0f)
        val newBlue = min(max( blue + (1.0f - blue) * factor, 0.0f), 1.0f)

        val newAlpha = when(isAlphaChannelAddOrSub) {
            true -> min(max( alpha + (1.0f - alpha) * factor, 0.0f), 1.0f)
            false -> min(max( alpha * (1.0f - factor), 0.0f), 1.0f)
            else -> alpha
        }

        return copy(
            red = newRed,
            green = newGreen,
            blue = newBlue,
            alpha = newAlpha
        )
    }

    private inline fun Color.addToRGBDynamic(factor: Float, isAlphaChannelAddOrSub: Boolean? = null): Color {
        return if (isDynamicColor)
            addToRGB(factor = factor, isAlphaChannelAddOrSub = isAlphaChannelAddOrSub) else
                this
    }

}