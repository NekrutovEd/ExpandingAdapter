package com.expanding.adapter.module.progress_bar

import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.PowerManager
import android.os.RemoteException
import android.view.animation.Interpolator
import android.view.animation.LinearInterpolator
import androidx.annotation.ColorInt
import androidx.annotation.UiThread
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.expanding.adapter.module.R


class CircularProgressDrawable(private val powerManager: PowerManager, private val options: Options) : Drawable(), Animatable {

    private var delegate =
        if (powerManager.isPowerSaveModeSafely) PowerSaveModeDelegate(this)
        else DefaultDelegate(this, options)
        set(value) {
            field.stop()
            field = value
        }

    private var isRunning = false

    private val bounds = RectF()
    private val paint = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeWidth = options.borderWidth
        strokeCap = if (options.style == CircularProgressStyle.STYLE_ROUNDED) Paint.Cap.ROUND else Paint.Cap.BUTT
        color = options.color
    }

    override fun start() {
        delegate =
            if (powerManager.isPowerSaveModeSafely) PowerSaveModeDelegate(this)
            else DefaultDelegate(this, options)
        delegate.start()
        isRunning = true
        invalidateSelf()
    }

    override fun stop() {
        isRunning = false
        delegate.stop()
        invalidateSelf()
    }

    override fun isRunning() = isRunning

    override fun getOpacity() = PixelFormat.TRANSLUCENT

    override fun draw(canvas: Canvas) {
        if (isRunning) delegate.draw(canvas, bounds, paint)
    }

    override fun setAlpha(alpha: Int) {
        paint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        paint.colorFilter = cf
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
        val border = (options.borderWidth / 2f) + .5f
        this.bounds.left = bounds.left + border
        this.bounds.right = bounds.right - border
        this.bounds.top = bounds.top + border
        this.bounds.bottom = bounds.bottom - border
    }

    @UiThread
    fun invalidate() {
        if (callback == null) stop()
        invalidateSelf()
    }

    fun progressiveStop(listener: (CircularProgressDrawable) -> Unit = {}) = delegate.progressiveStop(listener)

    enum class CircularProgressStyle {
        STYLE_NORMAL,
        STYLE_ROUNDED
    }

    class Options(
        val style: CircularProgressStyle = CircularProgressStyle.STYLE_ROUNDED,
        val sweepInterpolator: Interpolator = FastOutSlowInInterpolator(),
        val angleInterpolator: Interpolator = LinearInterpolator(),
        @ColorInt val color: Int,
        val borderWidth: Float,
        val sweepSpeed: Float,
        val rotationSpeed: Float,
        val minSweepAngle: Int,
        val maxSweepAngle: Int
    ) {
        companion object {
            fun createDefault(resources: Resources) = resources.run {
                Options(
                    style = CircularProgressStyle.STYLE_ROUNDED,
                    sweepInterpolator = FastOutSlowInInterpolator(),
                    angleInterpolator = LinearInterpolator(),
                    color = if (isApi23()) getColor(R.color.color, null) else getColor(R.color.color),
                    borderWidth = getDimension(R.dimen.stroke_width),
                    sweepSpeed = getString(R.string.sweep_speed).toFloat(),
                    rotationSpeed = getString(R.string.rotation_speed).toFloat(),
                    minSweepAngle = getInteger(R.integer.min_sweep_angle),
                    maxSweepAngle = getInteger(R.integer.max_sweep_angle)
                )
            }
        }
    }
}

private val PowerManager.isPowerSaveModeSafely: Boolean
    get() = if (isApi21()) {
        try {
            isPowerSaveMode
        } catch (e: RemoteException) {
            false
        }
    } else false

fun isApi19() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
fun isApi21() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
fun isApi23() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
