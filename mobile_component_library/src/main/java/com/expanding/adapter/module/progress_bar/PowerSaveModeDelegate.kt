package com.expanding.adapter.module.progress_bar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.os.SystemClock

internal class PowerSaveModeDelegate(private val parent: CircularProgressDrawable) : IProgressDelegate {

    private var currentRotation = 0f

    private val runnable = object : Runnable {
        override fun run() {
            currentRotation += 50
            currentRotation %= 360
            if (parent.isRunning) {
                parent.scheduleSelf(this, SystemClock.uptimeMillis() + REFRESH_RATE)
            }
            parent.invalidate()
        }
    }

    override fun draw(canvas: Canvas, bounds: RectF, paint: Paint) = canvas.drawArc(bounds, currentRotation, 300f, false, paint)

    override fun start() {
        parent.invalidate()
        parent.scheduleSelf(runnable, SystemClock.uptimeMillis() + REFRESH_RATE)
    }

    override fun stop() = parent.unscheduleSelf(runnable)

    override fun progressiveStop(listener: (CircularProgressDrawable) -> Unit) = parent.stop()

    companion object {
        private const val REFRESH_RATE: Long = 1000L
    }
}