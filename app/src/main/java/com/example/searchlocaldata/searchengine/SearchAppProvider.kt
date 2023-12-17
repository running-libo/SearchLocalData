package com.example.searchlocaldata.searchengine

import android.annotation.TargetApi
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.LauncherApps
import android.content.pm.PackageManager
import android.os.Build
import android.os.Process
import android.text.TextUtils
import com.example.searchlocaldata.bean.AppBean
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

object SearchAppProvider {
    fun searchInstallApps(context: Context): List<AppBean>? {
        return if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            checkInstallAppsBeforeL(context)
        } else {
            checkInstallAppsAfterL(context)
        }
    }

    private fun checkInstallAppsBeforeL(context: Context): List<AppBean> {
        val apps: MutableList<AppBean> = ArrayList()
        val pm = context.packageManager
        try {
            val packageInfos = pm.getInstalledPackages(0)
            for (i in packageInfos.indices) {
                val pkgInfo = packageInfos[i]
                val AppBean = pkgInfo.applicationInfo
                if (TextUtils.equals(context.packageName, pkgInfo.packageName)) continue
                val intent = getLaunchIntent(pm, pkgInfo.packageName)
                intent!!.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_CLEAR_TOP
                val app = AppBean(
                    pkgInfo.packageName,
                    AppBean.icon,
                    AppBean.loadLabel(pm).toString(),
                    intent
                )
                apps.add(app)
            }
        } catch (e: Exception) {
            //
        }


        return apps
    }

    /**
     * 以flow的方式处理数据
     */
    fun getLocalApps(context: Context) : Flow<List<AppBean>> = flow {
        val pm = context.packageManager
        val packageInfos = pm.getInstalledPackages(0)
        val apps = packageInfos.map { pkgInfo->
            val appBean = pkgInfo.applicationInfo
            val intent = getLaunchIntent(pm, pkgInfo.packageName)
            intent?.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_CLEAR_TOP
            AppBean(
                pkgInfo.packageName,
                appBean.icon,
                appBean.loadLabel(pm).toString(),
                intent
            )
        }
        emit(apps)
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun checkInstallAppsAfterL(context: Context): List<AppBean>? {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return null
        val launcherApps = context.getSystemService(Context.LAUNCHER_APPS_SERVICE) as LauncherApps
            ?: return null
        val apps: MutableList<AppBean> = ArrayList()
        try {
            val activityInfos = launcherApps.getActivityList(null, Process.myUserHandle())
            for (activityInfo in activityInfos) {
                val AppBean = activityInfo.applicationInfo
                val intent = Intent(Intent.ACTION_MAIN)
                intent.addCategory(Intent.CATEGORY_LAUNCHER)
                intent.setPackage(AppBean.packageName)
                intent.component = activityInfo.componentName
                intent.flags =
                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_CLEAR_TOP
                val app = AppBean(
                    AppBean.packageName,
                    AppBean.icon,
                    activityInfo.label.toString(),
                    intent
                )
                apps.add(app)
            }
        } catch (e: Exception) {
        }
        return apps
    }

    private fun getLaunchIntent(pm: PackageManager, pkg: String): Intent? {
        var intent = pm.getLaunchIntentForPackage(pkg)
        return if (intent != null) {
            intent
        } else {
            intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_LAUNCHER)
            intent.setPackage(pkg)
            val apps = pm.queryIntentActivities(intent, 0)
            if (apps == null || apps.isEmpty()) {
                return null
            }
            val ri = apps.iterator().next() ?: return null
            intent.component = ComponentName(pkg, ri.activityInfo.name)
            intent
        }
    }
}