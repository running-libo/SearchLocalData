package com.example.searchlocaldata.searchengine;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.LauncherActivityInfo;
import android.content.pm.LauncherApps;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Process;
import android.text.TextUtils;
import com.example.searchlocaldata.bean.AppBean;
import java.util.ArrayList;
import java.util.List;

public class SearchAppProvider {

    public static List<AppBean> searchInstallApps(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return checkInstallAppsBeforeL(context);
        } else {
            return checkInstallAppsAfterL(context);
        }
    }

    private static List<AppBean> checkInstallAppsBeforeL(Context context) {
        List<AppBean> apps = new ArrayList<>();
        PackageManager pm = context.getPackageManager();
        try {
            List<PackageInfo> packageInfos = pm.getInstalledPackages(0);
            for (int i = 0; i < packageInfos.size(); i++) {
                PackageInfo pkgInfo = packageInfos.get(i);
                ApplicationInfo AppBean = pkgInfo.applicationInfo;
                if (TextUtils.equals(context.getPackageName(), pkgInfo.packageName)) continue;
                Intent intent = getLaunchIntent(pm, pkgInfo.packageName);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                AppBean app = new AppBean(pkgInfo.packageName, AppBean.icon, AppBean.loadLabel(pm).toString(), intent);
                apps.add(app);
            }
        } catch (Exception e) {
            //
        }
        return apps;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private static List<AppBean> checkInstallAppsAfterL(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) return null;
        LauncherApps launcherApps = (LauncherApps) context.getSystemService(Context.LAUNCHER_APPS_SERVICE);
        if (launcherApps == null) return null;
        List<AppBean> apps = new ArrayList<>();
        try {
            List<LauncherActivityInfo> activityInfos = launcherApps.getActivityList(null, Process.myUserHandle());
            for (LauncherActivityInfo activityInfo : activityInfos) {
                ApplicationInfo AppBean = activityInfo.getApplicationInfo();
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                intent.setPackage(AppBean.packageName);
                intent.setComponent(activityInfo.getComponentName());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                AppBean app = new AppBean(AppBean.packageName, AppBean.icon, activityInfo.getLabel().toString(), intent);
                apps.add(app);
            }
        } catch (Exception e) {
        }
        return apps;
    }

    private static Intent getLaunchIntent(PackageManager pm, String pkg) {
        Intent intent = pm.getLaunchIntentForPackage(pkg);
        if (intent != null) {
            return intent;
        } else {
            intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setPackage(pkg);
            List<ResolveInfo> apps = pm.queryIntentActivities(intent, 0);
            if (apps == null || apps.isEmpty()) {
                return null;
            }
            ResolveInfo ri = apps.iterator().next();
            if (ri == null) {
                return null;
            }
            intent.setComponent(new ComponentName(pkg, ri.activityInfo.name));
            return intent;
        }
    }
}
