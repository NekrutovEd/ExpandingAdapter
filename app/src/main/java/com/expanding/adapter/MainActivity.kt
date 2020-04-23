package com.expanding.adapter

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.animation.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.expanding.adapter.module.FileItem
import com.expanding.adapter.module.SingleExpandingAdapter
import kotlinx.android.synthetic.main.activity_main.*
import java.io.ByteArrayOutputStream
import java.io.File.createTempFile
import java.io.FileOutputStream
import java.io.IOException


class MainActivity : AppCompatActivity(), IMain.View {

    private val adapter by lazy { SingleExpandingAdapter() }

    private val presenter : IMain.Presenter by lazy { MainPresenter(this, MainInteractor()) }

//    private var imageUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        imageUri = savedInstanceState?.getParcelable("imageUri")

        setContentView(R.layout.activity_main)

        rvFiles.layoutManager = LinearLayoutManager(this)

        rvFiles.adapter = adapter
        rvFiles.layoutAnimation = LayoutAnimationController(AnimationSet(false))
        rvFiles.setHasFixedSize(false)

//        share.setOnClickListener { presenter.share() }
//        downloadableView.setOnClickAddFileListener { downloadableView.addFileView(createDownloadableImageView()) }
//        downloadableView.setOnClickClearAllListener { downloadableView.clear() }
//        downloadableView.isEnabled = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 21
            )
        } else {
            val writeExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
            val readExternalStorage = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

            rvFiles.isEnabled = writeExternalStorage == PackageManager.PERMISSION_GRANTED
                    && readExternalStorage == PackageManager.PERMISSION_GRANTED
        }

        presenter
    }

    override fun showGroups(groups: List<FileItem>) {
        adapter.update(groups)
    }

//    override fun showGroups(groupId: Long, miniatures: List<MiniatureItem>) {
//        adapter.get
//    }

    override fun openFileDialog(fileId: Long) {
    }

//    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
//        outState?.putParcelable("imageUri", imageUri)
//        super.onSaveInstanceState(outState, outPersistentState)
//    }

//    private fun createDownloadableImageView(): DownloadableView.DownloadableModel {
//        val downloadableImageView = DownloadableView.DownloadableModel(this)
//
//        downloadableImageView.onClickWhenCompleteListener = {
//            downloadableImageView.downloadStatus = DownloadableImageView.DownloadStatus.Loading
//        }
//
//        downloadableImageView.onClickWhenLoadingListener = {
//            downloadableImageView.downloadStatus = DownloadableImageView.DownloadStatus.Complete
//        }
//
//        downloadableImageView.onClickWhenFailListener = {}
//        downloadableImageView.setOnLongClickListener {
//            downloadableView.removeFileView(it)
//            true
//        }
//
//        showImg(downloadableImageView.image_v)
//        return downloadableImageView
//    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == 21) {
            if (permissions.isNotEmpty() && grantResults.isNotEmpty() &&
                permissions[0] == Manifest.permission.WRITE_EXTERNAL_STORAGE && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && permissions[1] == Manifest.permission.READ_EXTERNAL_STORAGE && grantResults[1] == PackageManager.PERMISSION_GRANTED
            ) {
                rvFiles.isEnabled = true
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            super.onActivityResult(requestCode, resultCode, data)
        }

        when (requestCode) {

            ExampleDialogPresenter.GALLERY_IMAGE_REQUEST_CODE -> {
                val imageUri = data?.data ?: data?.extras?.getParcelable<Bitmap>("data")?.let { bitmap ->
                    try {
                        val bytes = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
                        val f = createTempFile(
                            "photo_",
                            ".jpg",
                            Environment.getExternalStorageDirectory()
                        )
                        f.deleteOnExit()
                        val fo = FileOutputStream(f)
                        fo.write(bytes.toByteArray())

                        Uri.fromFile(f)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        null
                    }
                }
                presenter.setUri(imageUri)
            }

            ExampleDialogPresenter.CAMERA_IMAGE_REQUEST_CODE -> {
//                data?.extras?.getParcelable<Bitmap>("data")?.also { bitmap ->
//                    try {
//                        val bytes = ByteArrayOutputStream()
//                        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
//                        val f = File(
//                            Environment.getExternalStorageDirectory()
//                                .toString() + File.separator + "temporary_file.jpg"
//                        )
//                        if (f.exists() && f.canWrite()) f.delete()
//                        f.createNewFile()
//                        val fo = FileOutputStream(f)
//                        fo.write(bytes.toByteArray())
//
//                        imageUri = Uri.parse("file:///sdcard/temporary_file.jpg")
//                    } catch (e: IOException) {
//                        e.printStackTrace()
//                    }
//                }
            }

            else -> {
                super.onActivityResult(requestCode, resultCode, data)
            }
        }
    }


//    private fun showImg(imageView: ImageView) {
//        Picasso.get()
//            .load(imageUri)
//            .resize(120, 120)
//            .centerCrop()
//            .placeholder(R.drawable.img_file_status_blank)
//            .into(imageView)
//    }
}
