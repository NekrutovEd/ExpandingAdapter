package com.expanding.adapter

import android.content.Intent
import android.provider.MediaStore

class ExampleDialogPresenter(private val view: MainActivity) {

    fun share() {
        processGalleryImageCCC2()
    }

    //Use  this method to select image_v from Gallery
    private fun processGalleryImageCCC2() {

        val intentCamera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

        val intentGallery = Intent(Intent.ACTION_PICK)
        intentGallery.type = "image/*"

        val intentDocument = Intent(Intent.ACTION_GET_CONTENT)
        intentDocument.type = "image/*"

        val intentChooser = Intent.createChooser(intentGallery, "Select Picture")
        intentChooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(intentCamera, intentDocument))
        view.startActivityForResult(intentChooser,
            GALLERY_IMAGE_REQUEST_CODE
        )
    }


    //Use  this method to select image_v from Gallery
    private fun processGalleryImageCCC() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        view.startActivityForResult(intent, GALLERY_IMAGE_REQUEST_CODE)
    }

    //Use  this method to select image_v from Gallery
    private fun processGalleryImage() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        val intentChooser = Intent.createChooser(intent, "Select Picture")
        view.startActivityForResult(intentChooser,
            GALLERY_MULTIPLE_REQUEST_CODE
        )
    }

    //Use  this method to click image_v from Camera
    private fun processCameraImage() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        view.startActivityForResult(cameraIntent, CAMERA_IMAGE_REQUEST_CODE)
    }

    companion object {
        const val GALLERY_IMAGE_REQUEST_CODE = 15
        const val GALLERY_MULTIPLE_REQUEST_CODE = 16
        const val CAMERA_IMAGE_REQUEST_CODE = 17
    }
}