package com.expanding.adapter

import android.net.Uri
import com.expanding.adapter.module.Group
import com.expanding.adapter.module.Miniature
import com.expanding.adapter.module.MiniatureItem
import java.util.*

fun createGroup(
    groupId: Long,
    title: String,
    hint: String,
    isEnabled: Boolean = true,
    miniatures: MutableList<Miniature> = mutableListOf()
): Group {
    return Group(
        groupId,
        title,
        hint,
        isEnabled,
        miniatures
    )
}

fun createGroup(
    number: Long,
    isEnabled: Boolean = true,
    miniatures: MutableList<Miniature> = mutableListOf()
): Group {
    return Group(
        number,
        "Title $number",
        "Hint $number",
        isEnabled,
        miniatures
    )
}

fun createMiniature(
    id: UUID = UUID.randomUUID(),
    fileId: Long = 0L,
    miniatureUri: Uri = Uri.EMPTY,
    status: MiniatureItem.MiniatureStatus = MiniatureItem.MiniatureStatus.Complete
): Miniature {
    return Miniature(
        id,
        fileId,
        miniatureUri,
        status
    )
}
