package com.expanding.adapter

import android.content.Intent
import android.net.Uri
import com.expanding.adapter.module.FileItem
import com.expanding.adapter.module.Group
import io.reactivex.rxjava3.core.Observable
import java.util.*

interface IMain {

    interface View {
        fun showGroups(groups: List<FileItem>)

        fun openFileDialog(fileId: Long)

        fun startActivityForResult(intentChooser: Intent?, galleryImageRequestCode: Int)

    }

    interface Presenter {
        fun setUri(imageUri: Uri?)

    }

    interface Interactor {

        fun loadGroups(): Observable<List<Group>>

        fun attachFile(groupId: Long?, imageUri: Uri?)

        fun clearGroup(groupId: Long)

        fun stopLoading(miniatureId: UUID)

        fun upload(miniatureId: UUID)

    }
}