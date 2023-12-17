package com.example.searchlocaldata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.searchlocaldata.ItemType.Companion.ITEM_TYPE_App
import com.example.searchlocaldata.ItemType.Companion.ITEM_TYPE_CONTACT
import com.example.searchlocaldata.ItemType.Companion.ITEM_TYPE_FILE
import com.example.searchlocaldata.databinding.ItemAppBinding
import com.example.searchlocaldata.databinding.ItemContactBinding
import com.example.searchlocaldata.databinding.ItemFileBinding
import com.example.searchlocaldata.databinding.ItemHeaderBinding

class MutipleAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList: List<ItemData> = ArrayList()

    public fun addData(itemList: List<ItemData>) {
        this.itemList = itemList
        notifyDataSetChanged()
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

        fun bind(item: ItemData) {
            binding.tvHeaderTitle.text = item.headerTitle
        }
    }

    inner class ViewHolderFile(val binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemData) {
            binding.tvFileName.text = item.headerTitle
        }
    }

    inner class ViewHolderContact(val binding: ItemContactBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemData) {
            binding.tvName.text = item.headerTitle
        }
    }

    inner class ViewHolderApp(val binding: ItemAppBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ItemData) {
            binding.tvAppName.text = item.headerTitle
        }
    }

}