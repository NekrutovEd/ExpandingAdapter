package com.expanding.adapter.module.progress_bar

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import androidx.annotation.UiThread


internal interface IProgressDelegate {

    @UiThread
    fun draw(canvas: Canvas, bounds: RectF, paint: Paint)

    @UiThread
    fun start()

    @UiThread
    fun stop()

    @UiThread
    fun progressiveStop(listener: (CircularProgressDrawable) -> Unit)
}