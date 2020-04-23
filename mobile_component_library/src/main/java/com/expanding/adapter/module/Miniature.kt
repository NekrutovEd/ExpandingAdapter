package com.expanding.adapter.module

import android.net.Uri
import java.util.*

data class Miniature(
    val id: UUID = UUID.randomUUID(),
    val fileId: Long = 0L,
    val miniatureUri: Uri = Uri.EMPTY,
    var status: MiniatureItem.MiniatureStatus = MiniatureItem.MiniatureStatus.Complete
)