package com.example.searchlocaldata.bean

import com.example.searchlocaldata.AdapterItem
import com.example.searchlocaldata.ItemType.Companion.ITEM_TYPE_FILE

data class FileBean(val fileName: String, val path: String, val fileSize: Int): AdapterItem() {

    init {
        itemType = ITEM_TYPE_FILE
    }
}