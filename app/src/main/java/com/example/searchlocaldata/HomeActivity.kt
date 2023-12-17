package com.example.searchlocaldata

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import com.example.searchlocaldata.databinding.ActivityHomeBinding
import com.example.searchlocaldata.searchengine.SearchAppProvider
import com.example.searchlocaldata.searchengine.SearchContactProvider
import com.example.searchlocaldata.searchengine.SearchFileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity: BasePermissionActivity(), BasePermissionActivity.PermissionListener {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: MutipleAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS,
            Manifest.permission.CALL_PHONE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_SETTINGS), this)

        initAdapter()

        binding.btnSearch.setOnClickListener {
            var key = binding.etSearch.text.toString()
            loadData(key)
        }
    }

    private fun initAdapter() {
        adapter = MutipleAdapter(this)
        binding.recyclerview.adapter = adapter
    }

    /**
     * 搜索各类数据
     */
    private fun loadData(key: String) {
        adapter.clearData()

        //搜索本地App
        val localAppsDeferred = GlobalScope.async(Dispatchers.IO) {
            SearchAppProvider.searchInstallApps(applicationContext)
        }

        //搜索联系人
        val contactsDeferred = GlobalScope.async(Dispatchers.IO) {
            SearchContactProvider.searchContact(applicationContext, key)
        }

        //搜索本地文件
        val localFilesDeferred = GlobalScope.async(Dispatchers.IO) {
            SearchFileProvider.searchLocalFile(applicationContext, key)
        }

        GlobalScope.launch {
            // 通过 await 获取异步任务的结果
            val localApps = localAppsDeferred.await()
            val contacts = contactsDeferred.await()
            val localFiles = localFilesDeferred.await()

            withContext(Dispatchers.Main) {
                adapter.appendData(AdapterItem(0, "本机应用"))
                adapter.appendDatas(localApps!!.take(10))

                adapter.appendData(AdapterItem(0, "联系人"))
                    .appendDatas(contacts)

                adapter.appendData(AdapterItem(0, "文件管理")).appendDatas(localFiles)  //先添加内容的header，再添加内容
            }
        }
    }

    override fun onGranted() {

    }

    override fun onDenied(deniedPermissions: MutableList<String>?) {

    }
}