package com.expanding.adapter

import com.expanding.adapter.module.Group
import com.expanding.adapter.module.MiniatureItem

fun createMockList(): MutableList<Group> {

    val result = mutableListOf<Group>()

    (0L..0).forEach {
        result.add(createGroup(it))
    }

    result.addAll(
        listOf(
            createGroup(
                100L,
                miniatures = mutableListOf(
                    createMiniature(),
                    createMiniature(status = MiniatureItem.MiniatureStatus.Loading),
                    createMiniature(status = MiniatureItem.MiniatureStatus.Fail)
                )
            )
        )
    )

    result.addAll(
        listOf(
            createGroup(
                200L,
                false,
                miniatures = mutableListOf(
                    createMiniature(),
                    createMiniature(status = MiniatureItem.MiniatureStatus.Loading),
                    createMiniature(status = MiniatureItem.MiniatureStatus.Fail)
                )
            ),
            createGroup(
                201L,
                false
            )
        )
    )

    return result
}