package com.example.searchlocaldata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.searchlocaldata.databinding.ActivityHomeBinding
import com.example.searchlocaldata.searchengine.SearchAppProvider
import com.example.searchlocaldata.searchengine.SearchFileProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: MutipleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()

        binding.btnSearch.setOnClickListener {
            var key = binding.etSearch.text.toString()
            loadData(key)
        }
    }

    private fun initAdapter() {
        adapter = MutipleAdapter(applicationContext)
        binding.recyclerview.adapter = adapter
    }

    /**
     * 搜索各类数据
     */
    private fun loadData(key: String) {
        //搜索本地App
        GlobalScope.launch {
            val apps = SearchAppProvider.searchInstallApps(applicationContext)
            withContext(Dispatchers.Main) {
                adapter.appendData(AdapterItem(0, "本机应用"))
                    .appendDatas(apps.take(10))
            }
        }
        //搜索联系人

        //搜索本地文件
        GlobalScope.launch {
            val localFiles = SearchFileProvider.searchLocalFile(applicationContext, key)
            withContext(Dispatchers.Main) {
                adapter.appendData(AdapterItem(0, "文件管理")).appendDatas(localFiles)  //先添加内容的header，再添加内容
            }
        }
    }
}