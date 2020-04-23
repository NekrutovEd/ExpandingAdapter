package com.expanding.adapter.module.progress_bar

import android.content.Context
import android.os.PowerManager
import android.util.AttributeSet
import android.widget.ProgressBar


class CircularProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyle: Int = 0) :
    ProgressBar(context, attrs, defStyle) {

    init {
        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val options = CircularProgressDrawable.Options.createDefault(context.resources)
        indeterminateDrawable = CircularProgressDrawable(powerManager, options)
    }

    fun progressiveStop(listener: (CircularProgressDrawable) -> Unit = {}) {
        (indeterminateDrawable as? CircularProgressDrawable)?.progressiveStop(listener)
            ?: error("The drawable is not a CircularProgressDrawable")
    }
}