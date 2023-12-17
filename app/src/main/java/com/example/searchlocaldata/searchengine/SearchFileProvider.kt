package com.example.searchlocaldata.searchengine

import android.content.Context
import android.database.Cursor
import android.provider.MediaStore
import android.text.TextUtils
import com.example.searchlocaldata.bean.FileBean
import java.io.File
import kotlin.collections.ArrayList

/**
 * 使用contentResolver查询本地各种文件
 */
object SearchFileProvider {
    private const val MAX_FILE_COUNT = 20

    /**
     * 模糊查询本地文件
     */
    suspend fun searchLocalFile(context: Context, key: String): List<FileBean> {
        var list = ArrayList<FileBean>()
        val volumeName = "external"
        val columns = arrayOf(MediaStore.Files.FileColumns.DATA)
        val selection = MediaStore.Files.FileColumns.DATA + " LIKE '%$key%.mp3' OR " +
                MediaStore.Files.FileColumns.DATA + " LIKE '%$key%.json' OR " +
                MediaStore.Files.FileColumns.DATA + " LIKE '%$key%.log' OR " +
                MediaStore.Files.FileColumns.DATA + " LIKE '%$key%.apk' OR " +
                MediaStore.Files.FileColumns.DATA + " LIKE '%$key%.mp4' OR " +
                MediaStore.Files.FileColumns.DATA + " LIKE '%$key%.pdf' OR " +
                MediaStore.Files.FileColumns.DATA + " LIKE '%$key%.txt' OR " +
                MediaStore.Files.FileColumns.DATA + " LIKE '%$key%.jpg' OR " +
                MediaStore.Files.FileColumns.DATA + " LIKE '%$key%.zip'"
        var cursor: Cursor? = null
        try {
            cursor = context.contentResolver.query(
                MediaStore.Files.getContentUri(volumeName),
                columns,
                selection,
                null,
                null
            )
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    if (list.size < MAX_FILE_COUNT) {
                        val absolutePath = cursor.getString(0)
                        File(absolutePath).apply {
                            if (exists() && !TextUtils.isEmpty(name) && name.contains(".")) {
                                if (!TextUtils.isEmpty(name)) {
                                    var bean = FileBean(name, path, readBytes().size)
                                    list.add(bean)
                                }
                            }
                        }
                    } else {
                        return list
                    }
                }
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        } finally {
            try {
                if (cursor != null) {
                    cursor.close()
                    cursor = null
                }
            } catch (e: java.lang.Exception) {
            }
        }
        return list
    }
}