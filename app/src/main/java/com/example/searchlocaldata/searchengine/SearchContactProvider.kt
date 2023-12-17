package com.example.searchlocaldata.searchengine

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.provider.ContactsContract
import android.provider.Settings
import android.util.Log
import com.example.searchlocaldata.bean.ContactBean

object SearchContactProvider {

    @SuppressLint("Range")
    fun readContacts(context: Context) {
        //ContactsContract.CommonDataKinds.Phone 联系人表
        var cursor: Cursor? = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            null, null, null, null)
        cursor?.let {
            while (it.moveToNext()) {
                //读取通讯录的姓名
                var name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                //读取通讯录的号码
                var number = cursor.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Log.i("minfo", "$name--$number")
            }
        }
    }

    /**
     * 模糊查询联系人
     */
    @SuppressLint("Range")
    fun searchContact(context: Context, key: String): List<ContactBean> {
        //ContactsContract.CommonDataKinds.Phone 联系人表
        var list = ArrayList<ContactBean>()
        val projection = arrayOf(
            ContactsContract.PhoneLookup.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        val selection = StringBuilder()
        selection.append(ContactsContract.Contacts.DISPLAY_NAME)
        selection.append(" LIKE '%$key%' ")
        var cursor: Cursor? = context.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            projection, selection.toString(), null, null)
        cursor?.let {
            while (it.moveToNext()) {
                //读取通讯录的姓名
                var name = it.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                //读取通讯录的号码
                var number = cursor.getString(it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                Log.i("minfo", "$name--$number")
                list.add(ContactBean(name, number))
            }
            it.close()
        }
        return list
    }

    /**
     * 模糊查询设置
     */
    @SuppressLint("Range")
    fun getSettings(context: Context, key: String) {
        val projection = arrayOf(
            Settings.System.NAME,
            Settings.System.VALUE
        )
        val selection = StringBuilder()
        selection.append(Settings.System.NAME)
        selection.append(" LIKE '%$key%' ")
        var cursor: Cursor? = context.contentResolver.query(Settings.System.CONTENT_URI,
            projection, selection.toString(), null, null)
        cursor?.let {
            while (it.moveToNext()) {
                var name = it.getString(it.getColumnIndex(Settings.System.NAME))
                Log.i("minfo", "name: " + "$name")
            }
            it.close()
        }
    }
}