package com.example.searchlocaldata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.searchlocaldata.databinding.ActivityHomeBinding
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
        adapter = MutipleAdapter(applicationContext).apply {
//            addData(
//                listOf(
//                    ItemData(0, "本机应用"),
//                    ItemData(3, "微信"),
//                    ItemData(3, "微信"),
//                    ItemData(3, "微信"),
//
//                    ItemData(0, "联系人"),
//                    ItemData(2, "dsfdsfds"),
//                    ItemData(2, "dsfdsfds"),
//                    ItemData(2, "dsfdsfds"),
//
//                    ItemData(0, "文件管理"),
//                    ItemData(1, "dsfdsfds.jpg"),
//                    ItemData(1, "dsfdsfds.jpg"),
//                    ItemData(1, "dsfdsfds.jpg"),
//                )
//            )
        }
        binding.recyclerview.adapter = adapter
    }

    private fun loadData(key: String) {
        //搜索本地App

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