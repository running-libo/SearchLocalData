package com.example.searchlocaldata

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FileAdapter
    private lateinit var etSearch: EditText
    private lateinit var btnSearch: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissions = arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        requestPermissions(permissions)

        recyclerView = findViewById(R.id.recyclerview)
        etSearch = findViewById(R.id.et_search)
        btnSearch = findViewById(R.id.btn_search)

        adapter = FileAdapter(applicationContext)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter


        btnSearch.setOnClickListener {
            var key = etSearch.text.toString()
            loadData(key)
        }
    }

    private fun loadData(key: String) {
//        val localFiles = SearchFileProvider.searchLocalFile(applicationContext, key)
//        adapter.setDatas(localFiles)
    }

    protected fun requestPermissions(
        permissions: Array<String>) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return
        }

        //记录未通过的权限
        val deniedPermissions: MutableList<String> = ArrayList()
        for (permission in permissions) {
            if (checkSelfPermission(permission) !== PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission)
            }
        }
        if (!deniedPermissions.isEmpty()) {
            requestPermissions(
                deniedPermissions.toTypedArray(),
                100
            )
        }
    }
}