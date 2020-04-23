package com.expanding.adapter.module

data class Group(
    val groupId: Long,
    val title: String,
    val hint: String,
    val isEnabled: Boolean,
    val miniatures: MutableList<Miniature>
)
