package com.expanding.adapter.module

import android.net.Uri
import android.view.View
import androidx.core.view.isVisible
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.downloadable_image_view.view.*

class MiniatureItem(
    private val miniature: Miniature,
    private var onClickWhenLoadingListener: ((MiniatureViewHolder) -> Unit) = {},
    private var onClickWhenFailListener: ((MiniatureViewHolder) -> Unit) = {},
    private var onClickWhenCompleteListener: ((MiniatureViewHolder) -> Unit) = {}
) : Item<MiniatureItem.MiniatureViewHolder>() {

    override fun getLayout(): Int = R.layout.downloadable_image_view

    override fun createViewHolder(itemView: View) = MiniatureViewHolder(itemView)

    override fun bind(viewHolder: MiniatureViewHolder, position: Int) {
        viewHolder.also {
            it.uri = miniature.miniatureUri
            it.downloadStatus = miniature.status
            it.onClickWhenLoadingListener = onClickWhenLoadingListener
            it.onClickWhenFailListener = onClickWhenFailListener
            it.onClickWhenCompleteListener = onClickWhenCompleteListener
        }
    }

    enum class MiniatureStatus {
        Loading,
        Fail,
        Complete
    }

    class MiniatureViewHolder(root: View) : GroupieViewHolder(root) {

        var uri: Uri? = null
            set(value) {
                notifyChangeUri(value)
                field = value
            }

        var downloadStatus = MiniatureStatus.Complete
            set(value) {
                notifyChangeStatus(value)
                field = value
            }

        var onClickWhenLoadingListener: ((MiniatureViewHolder) -> Unit) = {}
        var onClickWhenFailListener: ((MiniatureViewHolder) -> Unit) = {}
        var onClickWhenCompleteListener: ((MiniatureViewHolder) -> Unit) = {}

        init {
            root.isClickable = true
            root.setOnClickListener { performClick() }
        }

        private fun performClick() {
            when (downloadStatus) {
                MiniatureStatus.Loading -> { holder: MiniatureViewHolder -> root.progressBar.progressiveStop { onClickWhenLoadingListener(holder) } }
                MiniatureStatus.Fail -> onClickWhenFailListener
                MiniatureStatus.Complete -> onClickWhenCompleteListener
            }.invoke(this)
        }

        private fun notifyChangeUri(uri: Uri?) {
            Picasso.get()
                .load(uri)
                .placeholder(R.drawable.ic_pill)
                .resizeDimen(R.dimen.miniature_width, R.dimen.miniature_height)
                .centerInside()
                .into(root.imageView)
        }

        private fun notifyChangeStatus(status: MiniatureStatus) {
            with(root) {
                when (status) {
                    MiniatureStatus.Loading -> {
                        progressBarLayout.isVisible = true
                        failLayout.isVisible = false
                    }
                    MiniatureStatus.Fail -> {
                        progressBarLayout.isVisible = false
                        failLayout.isVisible = true
                    }
                    MiniatureStatus.Complete -> {
                        progressBarLayout.isVisible = false
                        failLayout.isVisible = false
                    }
                }
            }
        }
    }
}