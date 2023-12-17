package com.example.searchlocaldata

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.searchlocaldata.databinding.ActivityHomeBinding

class HomeActivity: AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initAdapter()
    }

    private fun initAdapter() {
        binding.recyclerview.adapter = MutipleAdapter().apply {
            addData(
                listOf(
                    ItemData(0, "本机应用"),
                    ItemData(3, "微信"),
                    ItemData(3, "微信"),
                    ItemData(3, "微信"),

                    ItemData(0, "联系人"),
                    ItemData(2, "dsfdsfds"),
                    ItemData(2, "dsfdsfds"),
                    ItemData(2, "dsfdsfds"),

                    ItemData(0, "文件管理"),
                    ItemData(1, "dsfdsfds.jpg"),
                    ItemData(1, "dsfdsfds.jpg"),
                    ItemData(1, "dsfdsfds.jpg"),
                )
            )
        }
    }
}