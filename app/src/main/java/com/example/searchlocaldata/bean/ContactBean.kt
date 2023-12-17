package com.example.searchlocaldata.bean

import com.example.searchlocaldata.AdapterItem
import com.example.searchlocaldata.ItemType

class ContactBean(val name: String, val number: String): AdapterItem() {

    init {
        itemType = ItemType.ITEM_TYPE_CONTACT
    }
}