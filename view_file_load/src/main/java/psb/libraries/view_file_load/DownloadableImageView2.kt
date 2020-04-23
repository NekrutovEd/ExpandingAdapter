//package psb.libraries.view_file_load
//
//import android.content.Context
//import android.os.Parcelable
//import android.util.AttributeSet
//import android.view.MotionEvent
//import android.view.View
//import android.widget.FrameLayout
//import android.widget.ImageView
//import androidx.core.view.isVisible
//import kotlinx.android.parcel.Parcelize
//import kotlinx.android.synthetic.main.downloadable_image_view.view.*
//
//class DownloadableImageView2 @JvmOverloads constructor(
//    context: Context,
//    attrs: AttributeSet? = null,
//    defStyleAttr: Int = 0
//) : FrameLayout(context, attrs, defStyleAttr) {
//
//    var downloadStatus = DownloadStatus.Complete
//        set(value) {
//            changeView(value)
//            field = value
//        }
//
//    var onClickWhenLoadingListener: (() -> Unit)? = null
//    var onClickWhenFailListener: (() -> Unit)? = null
//    var onClickWhenCompleteListener: (() -> Unit)? = null
//
//    init {
//        View.inflate(context, R.layout.downloadable_image_view, this)
//        isClickable = true
//    }
//
//    val image_v: ImageView get() = imageView
//
//    override fun onSaveInstanceState(): Parcelable? = DownloadableImageState(downloadStatus, super.onSaveInstanceState())
//
//    override fun onRestoreInstanceState(state: Parcelable?) {
//        val downloadableState = (state as? DownloadableImageState)
//        downloadableState?.downloadStatus?.let { downloadStatus = it }
//        super.onRestoreInstanceState(downloadableState?.superState)
//    }
//
//    override fun performClick(): Boolean {
//        val listener = when (downloadStatus) {
//            DownloadStatus.Loading -> onClickWhenLoadingListener
//            DownloadStatus.Fail -> onClickWhenFailListener
//            DownloadStatus.Complete -> onClickWhenCompleteListener
//        } ?: return super.performClick()
//        listener()
//
//        return true
//    }
//
//    private fun changeView(status: DownloadStatus) {
//        when (status) {
//            DownloadStatus.Loading -> {
//                progressBarLayout.isVisible = true
//                failLayout.isVisible = false
//            }
//            DownloadStatus.Fail -> {
//                progressBarLayout.isVisible = false
//                failLayout.isVisible = true
//            }
//            DownloadStatus.Complete -> {
//                progressBarLayout.isVisible = false
//                failLayout.isVisible = false
//            }
//        }
//    }
//
//    private fun showContent() {
//
//    }
//
//    @Parcelize
//    enum class DownloadStatus : Parcelable {
//        Loading,
//        Fail,
//        Complete
//    }
//
//    @Parcelize
//    class DownloadableImageState(val downloadStatus: DownloadStatus, private val _superState: Parcelable?) : BaseSavedState(_superState)
//}