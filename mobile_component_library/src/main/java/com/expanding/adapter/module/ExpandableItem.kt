package com.expanding.adapter.module

import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.Transformation
import androidx.core.view.isVisible
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item

abstract class ExpandableItem<VH : ExpandableItem.ExpandableViewHolder> : Item<VH>() {

    var isExpanded = false
        private set

    abstract class ExpandableViewHolder(root: View) : GroupieViewHolder(root) {

        abstract val head: View
        abstract val body: View

        protected val animationPlaybackSpeed: Double = 0.7

        private val animationDuration: Long get() = (300L / animationPlaybackSpeed).toLong()

        fun expand(item: ExpandableItem<*>, animated: Boolean) {
            item.changeHeight(true, animated, startAnimation = {
                val initialHeight = body.layoutParams.height
                body.measureHeightToWrapContent()
                val deltaHeight = body.measuredHeight - initialHeight

                body.isVisible = true
                startAnimation { interpolatedTime ->
                    if (interpolatedTime == 1f) {
                        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
                    } else {
                        layoutParams.height = (initialHeight + (deltaHeight * interpolatedTime)).toInt()
                    }
                    requestLayout()
                }
            })
        }

        fun collapse(item: ExpandableItem<*>, animated: Boolean) {
            item.changeHeight(false, animated, startAnimation = {
                val initialHeight = body.measuredHeight
                startAnimation { interpolatedTime ->
                    if (interpolatedTime == 1f) {
                        isVisible = false
                        layoutParams.height = 0
                    } else {
                        layoutParams.height = (initialHeight - (initialHeight * interpolatedTime)).toInt()
                    }
                    requestLayout()
                }
            })
        }

        private fun ExpandableItem<*>.changeHeight(expand: Boolean, animated: Boolean, startAnimation: () -> Unit) {
            isExpanded = expand

            if (id != item?.id) return

            if (animated) {
                startAnimation()
            } else {
                body.isVisible = expand
                body.layoutParams.height = if (expand) ViewGroup.LayoutParams.WRAP_CONTENT else 0
                body.requestLayout()
            }
        }

        private fun startAnimation(applyTransformation: View.(interpolatedTime: Float) -> Unit) {
            val animation = object : Animation() {
                override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
                    body.applyTransformation(interpolatedTime)
                }
            }
            animation.duration = animationDuration
            body.startAnimation(animation)
        }

        private fun View.measureHeightToWrapContent() {
            val matchParent = View.MeasureSpec.makeMeasureSpec((parent as View).width, View.MeasureSpec.EXACTLY)
            val wrapContent = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            measure(matchParent, wrapContent)
        }
    }
}