package com.example.searchlocaldata

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class FileAdapter(val context: Context): RecyclerView.Adapter<FileAdapter.FileViewHolder>() {
    private var datas = ArrayList<FileBean>()

    fun setDatas(datas: ArrayList<FileBean>) {
        this.datas = datas
        notifyDataSetChanged()
    }

    class FileViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var ivIcon: ImageView?= null
        var tvName: TextView?= null

        init {
            ivIcon = itemView.findViewById(R.id.iv_icon)
            tvName = itemView.findViewById(R.id.tv_file_name)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewHolder {
        return FileViewHolder(LayoutInflater.from(context).inflate(R.layout.item_file, parent, false))
    }

    override fun onBindViewHolder(holder: FileViewHolder, position: Int) {
        holder.tvName?.text = datas[position].fileName
        setFileIcon(holder.ivIcon!!, datas[position].fileName)

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return datas.size
    }

    /**
     * 根据文件后缀类型设置对应类型图标
     */
    private fun setFileIcon(imageView: ImageView, fileName: String) {
        if (fileName.endsWith(".jpg") || fileName.endsWith(".mp4")) {
            imageView.background = context.resources.getDrawable(R.drawable.category_file_icon_pic_phone)
        } else {
            var drawableId = 0
            if (fileName.endsWith(".txt") || fileName.endsWith(".pdf")) {
                drawableId = R.drawable.category_file_icon_doc_phone
            } else if (fileName.endsWith(".zip")) {
                drawableId = R.drawable.category_file_icon_zip_phone
            } else if (fileName.endsWith(".mp3")) {
                drawableId = R.drawable.category_file_icon_music_phone
            } else if (fileName.endsWith(".apk")) {
                drawableId = R.drawable.category_file_icon_apk_phone
            } else {
                drawableId = R.drawable.ic_local_file
            }
            imageView.background = context.resources.getDrawable(drawableId)
        }
    }
}