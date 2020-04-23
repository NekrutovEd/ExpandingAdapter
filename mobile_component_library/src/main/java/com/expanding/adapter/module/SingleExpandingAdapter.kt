package com.expanding.adapter.module

import com.xwray.groupie.GroupieViewHolder

class SingleExpandingAdapter : ExpandingAdapter() {

    private var expandedItem: ExpandableItem<*>? = null
    private var oldExpandedHolder: ExpandableItem.ExpandableViewHolder? = null

    override fun onBindViewHolder(holder: GroupieViewHolder, position: Int, payloads: List<Any>) {
        super.onBindViewHolder(holder, position, payloads)
        if (holder is ExpandableItem.ExpandableViewHolder) {
            val group = getGroupAtAdapterPosition(position)
            if (group is ExpandableItem<*>) {
                oldExpandedHolder = holder.takeIf { group.id == expandedItem?.id } ?: oldExpandedHolder
                holder.expandItem(group, expand = group.id == expandedItem?.id, animate = false)

                holder.head.setOnClickListener {
                    holder.expandItem(group, expand = group.id != expandedItem?.id)
                    expandedItem?.takeIf { group.id != it.id }?.also {
                        oldExpandedHolder?.expandItem(it, expand = false)
                    }
                    oldExpandedHolder = holder.takeUnless { group.id == expandedItem?.id }
                    expandedItem = group.takeUnless { it.id == expandedItem?.id }
                }
            }
        }
    }
}
