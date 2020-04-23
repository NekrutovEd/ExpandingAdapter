//package psb.libraries.view_file_load
//
//import android.net.Uri
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//
//class DownloadableImageViewAdapter : RecyclerView.Adapter<DownloadableImageViewHolder>() {
//
//    var files: MutableList<DownloadableView.DownloadableModel> = mutableListOf()
//        set(value) {
//            field = value
//            notifyDataSetChanged()
//        }
//
//    fun addFile(model: DownloadableView.DownloadableModel) {
//        files.add(model)
//        notifyDataSetChanged()
//    }
//
//    fun removeFile(uri: Uri) {
//        files.removeAll { it.uri == uri }
//        notifyDataSetChanged()
//    }
//
//    override fun getItemCount() = files.size
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DownloadableImageViewHolder {
//        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.downloadable_image_view, parent, false)
//        val viewHolder = DownloadableImageViewHolder(view)
//        viewHolder.onClickWhenCompleteListener = {  }
//        viewHolder.onClickWhenLoadingListener = {  }
//        viewHolder.onClickWhenFailListener = {  }
//        return viewHolder
//    }
//
//    override fun onBindViewHolder(holder: DownloadableImageViewHolder, position: Int) {
//        holder.uri = files[position].uri
//        holder.downloadStatus = files[position].status
//    }
//}