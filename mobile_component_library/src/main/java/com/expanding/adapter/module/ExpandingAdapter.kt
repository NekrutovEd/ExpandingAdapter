package com.expanding.adapter.module

import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.GroupieViewHolder

open class ExpandingAdapter : GroupAdapter<GroupieViewHolder>() {

    override fun onBindViewHolder(holder: GroupieViewHolder, position: Int, payloads: List<Any>) {
        super.onBindViewHolder(holder, position, payloads)

        if (holder !is ExpandableItem.ExpandableViewHolder) return
        val group = getGroupAtAdapterPosition(position) as? ExpandableItem<*> ?: return

        holder.expandItem(group, expand = group.isExpanded, animate = false)
        holder.head.setOnClickListener { holder.expandItem(group, expand = !group.isExpanded) }
    }

    protected fun ExpandableItem.ExpandableViewHolder.expandItem(group: ExpandableItem<*>, expand: Boolean, animate: Boolean = true) {
        if (expand) expand(group, animate) else collapse(group, animate)
    }
}
