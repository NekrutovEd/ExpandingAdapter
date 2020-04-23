package com.expanding.adapter.module.progress_bar

import android.animation.ValueAnimator
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.view.animation.LinearInterpolator
import androidx.core.animation.addListener

internal class DefaultDelegate(
    private val parent: CircularProgressDrawable,
    private val options: CircularProgressDrawable.Options
) : IProgressDelegate {

    private var sweepAnimator = ValueAnimator.ofInt(options.minSweepAngle, options.maxSweepAngle)
    private var rotationAnimator = ValueAnimator.ofFloat(0f, 360f)
    private var endAnimator = ValueAnimator.ofFloat(0f, 360f)

    private var currentSweepAngleForEnd = 0f
    private var deltaSweepAngleForEnd = 0f

    private var modeAppearing = true
    private var currentRotationAngleOffset = 0f

    private var currentSweepAngle = 0f
        set(value) {
            field = value
            parent.invalidate()
        }

    private var currentRotationAngle = 0f
        set(value) {
            field = value
            parent.invalidate()
        }


    init {
        rotationAnimator.apply {
            interpolator = options.angleInterpolator
            duration = (ROTATION_ANIMATOR_DURATION / options.rotationSpeed).toLong()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.RESTART
            addUpdateListener { currentRotationAngle = it.animatedFraction * 360f }
        }

        val deltaSweepAngle = options.maxSweepAngle - options.minSweepAngle
        sweepAnimator.apply {
            interpolator = options.sweepInterpolator
            duration = (SWEEP_ANIMATOR_DURATION / options.sweepSpeed).toLong()
            repeatCount = ValueAnimator.INFINITE
            repeatMode = ValueAnimator.REVERSE
            addUpdateListener { currentSweepAngle = options.minSweepAngle + (it.animatedFraction * deltaSweepAngle) }
            addListener(
                onStart = {
                    modeAppearing = true
                    currentSweepAngle = options.minSweepAngle.toFloat()
                },
                onRepeat = {
                    currentRotationAngleOffset += if (modeAppearing) 360 - options.maxSweepAngle else options.minSweepAngle
                    modeAppearing = !modeAppearing
                }
            )
        }

        endAnimator.apply {
            interpolator = LinearInterpolator()
            duration = (END_ANIMATOR_DURATION / options.sweepSpeed).toLong()
            addUpdateListener { currentSweepAngle = currentSweepAngleForEnd + (it.animatedFraction * deltaSweepAngleForEnd) }
        }
    }

    override fun draw(canvas: Canvas, bounds: RectF, paint: Paint) {
        val sweepAngle = currentSweepAngle
        var startAngle = currentRotationAngle - currentRotationAngleOffset
        if (!modeAppearing) startAngle += 360 - sweepAngle
        startAngle %= 360f
        canvas.drawArc(bounds, startAngle, sweepAngle, false, paint)
    }

    override fun start() {
        endAnimator.cancel()
        rotationAnimator.start()
        sweepAnimator.start()
    }

    override fun stop() {
        sweepAnimator.cancel()
        rotationAnimator.cancel()
        endAnimator.cancel()
    }

    override fun progressiveStop(listener: (CircularProgressDrawable) -> Unit) {
        if (!parent.isRunning || endAnimator.isRunning) return

        endAnimator.addListener(
            onStart = {
                sweepAnimator.cancel()
                if (!modeAppearing) {
                    modeAppearing = true
                    currentRotationAngleOffset += currentSweepAngle
                }
            },
            onEnd = {
                currentSweepAngleForEnd = 0f
                deltaSweepAngleForEnd = 0f
                endAnimator.removeAllListeners()
                parent.stop()
                listener(parent)
            }
        )
        currentSweepAngleForEnd = currentSweepAngle
        deltaSweepAngleForEnd = 360 - currentSweepAngleForEnd
        endAnimator.duration = ((END_ANIMATOR_DURATION / options.sweepSpeed) * ((360 - currentSweepAngle) / 360)).toLong()
        endAnimator.start()
    }

    companion object {
        private const val ROTATION_ANIMATOR_DURATION: Long = 1000
        private const val SWEEP_ANIMATOR_DURATION: Long = 750
        private const val END_ANIMATOR_DURATION: Long = 750
    }
}
