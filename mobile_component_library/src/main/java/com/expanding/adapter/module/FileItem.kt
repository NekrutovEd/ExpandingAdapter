package com.expanding.adapter.module

import android.view.View
import androidx.core.view.isVisible
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder
import kotlinx.android.synthetic.main.downloadable_view.view.*

class FileItem(
    private val groupId: Long,
    private val title: String,
    private val hint: String,
    private val isEnabledItem: Boolean,
    private val miniatures: List<MiniatureItem>,
    private val onClickAddFileListener: (View) -> Unit,
    private val onClickClearAllListener: (View) -> Unit
) : ExpandableItem<FileItem.FileViewHolder>() {

    override fun getLayout(): Int = R.layout.downloadable_view

    override fun createViewHolder(itemView: View): FileViewHolder {
        return FileViewHolder(itemView)
    }

    override fun bind(viewHolder: FileViewHolder, position: Int) {
        viewHolder.root.apply {
            tvTitle.text = title
            tvHint.text = hint

            clHead.isEnabled = isEnabledItem
            tvTitle.isEnabled = isEnabledItem
            clBody.isVisible = isEnabledItem && clBody.isVisible

            viewHolder.updateMiniatures(miniatures)

            btnAddFile.setOnClickListener(onClickAddFileListener.takeIf { isEnabledItem })
            btnClearAll.setOnClickListener(onClickClearAllListener.takeIf { isEnabledItem })
        }
    }

    override fun getId() = groupId

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FileItem

        if (groupId != other.groupId) return false
        if (title != other.title) return false
        if (hint != other.hint) return false
        if (isEnabledItem != other.isEnabledItem) return false
        if (miniatures != other.miniatures) return false

        return true
    }

    override fun hashCode(): Int {
        var result = groupId.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + hint.hashCode()
        result = 31 * result + isEnabledItem.hashCode()
        result = 31 * result + miniatures.hashCode()
        return result
    }

    class FileViewHolder(root: View) : ExpandableViewHolder(root) {

        override val head: View get() = root.clHead
        override val body: View get() = root.clBody

        private val groupAdapter = GroupAdapter<GroupieViewHolder>()

        init {
            root.rvMiniature.adapter = groupAdapter
        }

        fun updateMiniatures(miniatures: List<MiniatureItem>) {
            if (miniatures.isNotEmpty()) {
                root.btnClearAll.isVisible = true
                root.rvMiniature.isVisible = true
                if (head.isEnabled) {
                    head.ivStatusImage.setImageResource(R.drawable.img_file_status_filled)
                } else {
                    head.ivStatusImage.setImageResource(R.drawable.img_file_status_blank)
                }
            } else {
                root.btnClearAll.isVisible = false
                root.rvMiniature.isVisible = false
                head.ivStatusImage.setImageResource(R.drawable.img_file_status_blank)
            }
            groupAdapter.update(miniatures)
        }
    }
}
