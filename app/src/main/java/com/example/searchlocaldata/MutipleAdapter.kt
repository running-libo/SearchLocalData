package com.example.searchlocaldata

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.searchlocaldata.ItemType.Companion.ITEM_TYPE_HEADER
import com.example.searchlocaldata.databinding.ItemHeaderBinding

class MutipleAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var itemList: List<ItemData> = ArrayList()

    public fun addData(itemList: List<ItemData>) {
        this.itemList = itemList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == ITEM_TYPE_HEADER) {
            return ViewHolderHeader(ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        } else {
            return ViewHolderHeader(ItemHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false))
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
        }
    }

    private class ViewHolderHeader(val binding: ItemHeaderBinding) : RecyclerView.ViewHolder(binding.root) {

        public fun bind(item: ItemData) {
            binding.tvHeaderTitle.text = item.headerTitle
        }
    }

}