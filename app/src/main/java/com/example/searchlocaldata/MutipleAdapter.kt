package com.example.searchlocaldata

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.searchlocaldata.ItemType.Companion.ITEM_TYPE_App
import com.example.searchlocaldata.ItemType.Companion.ITEM_TYPE_CONTACT
import com.example.searchlocaldata.ItemType.Companion.ITEM_TYPE_FILE
import com.example.searchlocaldata.bean.AppBean
import com.example.searchlocaldata.bean.FileBean
import com.example.searchlocaldata.databinding.ItemAppBinding
import com.example.searchlocaldata.databinding.ItemContactBinding
import com.example.searchlocaldata.databinding.ItemFileBinding
import com.example.searchlocaldata.databinding.ItemHeaderBinding
import java.util.concurrent.CopyOnWriteArrayList

class MutipleAdapter(val context: Context): RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList = CopyOnWriteArrayList<AdapterItem>()

    fun appendDatas(datas: List<AdapterItem>): MutipleAdapter {
        this.itemList.addAll(datas)
        notifyDataSetChanged()
        return this
    }

    fun appendData(data: AdapterItem): MutipleAdapter {
        this.itemList.add(data)
        return this
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_TYPE_FILE) {
            ViewHolderFile(ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else if (viewType == ITEM_TYPE_CONTACT) {
            ViewHolderContact(ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else if (viewType == ITEM_TYPE_App) {
            ViewHolderApp(ItemAppBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            ViewHolderHeader(ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun getItemCount() = itemList.size

    override fun getItemViewType(position: Int): Int {
        return itemList[position].itemType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var itemData = itemList[position]
        if (holder is ViewHolderHeader) {
            holder.bind(itemData)
        } else if (holder is ViewHolderFile) {
            holder.bind(itemData)
        } else if (holder is ViewHolderContact) {
            holder.bind(itemData)
        } else if (holder is ViewHolderApp) {
            holder.bind(itemData)
        }
    }

    inner class ViewHolderHeader(val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AdapterItem) {
            binding.tvHeaderTitle.text = item.headerTitle
        }
    }

    inner class ViewHolderFile(val binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AdapterItem) {
            if (item is FileBean) {
                binding.tvFileName.text = item.fileName
                binding.tvFileInfo.text = "" + (item.fileSize/1000) + "KB"

                setFileIcon(binding.ivIcon, item.fileName)
            }
        }
    }

    inner class ViewHolderContact(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AdapterItem) {
            binding.tvName.text = item.headerTitle
        }
    }

    inner class ViewHolderApp(val binding: ItemAppBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: AdapterItem) {
            if (item is AppBean) {
                binding.tvAppName.text = item.name
                binding.ivIcon.background = context.packageManager.getActivityIcon(item.intent)
            }
        }
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