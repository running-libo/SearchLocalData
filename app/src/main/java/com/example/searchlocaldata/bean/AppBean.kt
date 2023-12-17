package com.example.searchlocaldata.bean

import android.content.Intent
import com.example.searchlocaldata.AdapterItem
import com.example.searchlocaldata.ItemType

class AppBean(val pkg: String, val icon: Int, val name: String, val intent: Intent): AdapterItem() {

    init {
        itemType = ItemType.ITEM_TYPE_App
    }
}