package com.example.searchlocaldata

interface ItemType {

    companion object {
        const val ITEM_TYPE_HEADER: Int = 0 //header
        const val ITEM_TYPE_App = 1 //本地应用
        const val ITEM_TYPE_CONTACT = 2 //联系人
        const val ITEM_TYPE_FILE: Int = 3 //本地文件
    }

}