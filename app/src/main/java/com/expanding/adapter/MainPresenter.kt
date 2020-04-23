package com.expanding.adapter

import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import com.expanding.adapter.module.FileItem
import com.expanding.adapter.module.MiniatureItem


class MainPresenter(
    private val view: IMain.View,
    private val interactor: IMain.Interactor
) : IMain.Presenter {

    private var groupId: Long? = null

    init {
        interactor.loadGroups()
            .subscribe {groups ->

                val fileItems = groups.map { group ->
                    FileItem(
                        group.groupId,
                        group.title,
                        group.hint,
                        group.isEnabled,
                        group.miniatures.map { miniature ->
                            MiniatureItem(
                                miniature,
                                { it.downloadStatus = MiniatureItem.MiniatureStatus.Fail },
                                { it.downloadStatus = MiniatureItem.MiniatureStatus.Complete },
                                { it.downloadStatus = MiniatureItem.MiniatureStatus.Loading }
                            )
                        },
                        { processGalleryImageCCC2(group.groupId) },
                        { interactor.clearGroup(group.groupId) }
                    )
                }
                view.showGroups(fileItems)

            }
    }

    //Use  this method to select image_v from Gallery
    private fun processGalleryImageCCC2(groupId:Long) {
        this.groupId = groupId
        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type = "image/*"

        val intentDocument = Intent(Intent.ACTION_GET_CONTENT)
        intentDocument.type = "image/*"

        val intentChooser = Intent.createChooser(intentGallery, "Select Picture")
        intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(intentCamera, intentDocument))
        view.startActivityForResult(intentChooser, ExampleDialogPresenter.GALLERY_IMAGE_REQUEST_CODE)
    }

    override fun setUri(imageUri: Uri?) {
        interactor.attachFile(groupId, imageUri)
    }
}