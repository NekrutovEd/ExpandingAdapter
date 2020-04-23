package psb.libraries.view_file_load

import android.net.Uri
import java.util.*

//
//import android.content.Context
//import android.net.Uri
//import android.os.Parcelable
//import android.util.AttributeSet
//import android.view.View
//import androidx.cardview.widget.CardView
//import androidx.core.view.isVisible
//import androidx.recyclerview.widget.DiffUtil
//import androidx.recyclerview.widget.ListAdapter
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.parcel.Parcelize
//import kotlinx.android.synthetic.main.downloadable_view.view.*
//import java.util.*
//
//class DownloadableView @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : CardView(context, attrs, defStyleAttr) {
//
//    private var counter = 0
//        set(value) {
//            btnClearAll.isVisible = value > 0
//            field = value
//        }
//
//
//    private val adapter = DownloadableImageViewAdapter()
//
//    init {
//        View.inflate(context, R.layout.downloadable_view, this)
//        initTouchListeners()
//
//        rvFiles.adapter = adapter
//
//        counter = 0
//    }
//
//    private fun initTouchListeners() {
//        clHead.setOnClickListener {
//            clBody.isVisible = !clBody.isVisible
//        }
//    }
//
//    override fun setEnabled(enabled: Boolean) {
//        super.setEnabled(enabled)
//        clHead.isEnabled = enabled
//        tvTitle.isEnabled = enabled
//        clBody.isVisible = enabled && clBody.isVisible
//    }
//
//    fun setOnClickAddFileListener(listener: (View) -> Unit) = btnAddFile.setOnClickListener(listener)
//
//    fun setOnClickClearAllListener(listener: (View) -> Unit) = btnClearAll.setOnClickListener(listener)
//
//    fun setTitle(title: String) {
//        tvTitle.text = title
//    }
//
//    fun setHint(hint: String) {
//        tvHint.text = hint
//    }
//
//
//    override fun onSaveInstanceState(): Parcelable? {
//        return DownloadableState(clBody.isVisible, super.onSaveInstanceState())
//    }
//
//    override fun onRestoreInstanceState(state: Parcelable?) {
//        val downloadableState = (state as? DownloadableState)
//        downloadableState?.clBodyIsVisible?.let { clBody.isVisible = it }
//        super.onRestoreInstanceState(downloadableState?.superState)
//    }
//
//    fun updateMiniatures(miniatures: List<Miniature>) {
//        adapter.files
//    }
//
//    @Parcelize
//    class DownloadableState(
//
//        val clBodyIsVisible: Boolean,
//        private val _superState: Parcelable?
//    ) : BaseSavedState(_superState)
//
//    data class DownloadableModel(
//        val id: String,
//        val uri: Uri
//    ) {
//        var status: MiniatureItem.MiniatureStatus = MiniatureItem.MiniatureStatus.Complete
//        var onClickWhenLoadingListener: ((Uri?) -> Unit) = {}
//        var onClickWhenFailListener: ((Uri?) -> Unit) = {}
//        var onClickWhenCompleteListener: ((Uri?) -> Unit) = {}
//    }
//}
//
//
//
data class Group(val groupId: Long, val title: String, val hint: String, val isEnabled: Boolean, val miniatures: MutableList<Miniature>)
data class Miniature(val id: UUID, val fileId: Long, val miniatureUri: Uri, var status: psb.libraries.test.MiniatureItem.MiniatureStatus)
data class File(val file: Any)
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
//
