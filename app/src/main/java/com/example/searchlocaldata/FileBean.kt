package com.example.searchlocaldata

import com.example.searchlocaldata.ItemType.Companion.ITEM_TYPE_FILE

data class FileBean(val fileName: String, val path: String, val fileSize: Int): AdapterItem() {

    init {
        itemType = ITEM_TYPE_FILE
    }
}