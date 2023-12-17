package com.example.searchlocaldata;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import androidx.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * description 6.0权限检查基类
 */
public class BasePermissionActivity extends Activity {
    private PermissionListener permissionListener;
    private static final int PERMISSION_REQUESTCODE = 100;
    /** 是否询问过一遍请求权限 */
    private boolean isAskedPermission;

    /**
     * 请求需要通过的权限
     * @param permissions
     * @return
     */
    protected void requestPermissions(String[] permissions,PermissionListener permissionListener) {
        this.permissionListener = permissionListener;

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }

        //记录未通过的权限
        List<String> deniedPermissions = new ArrayList<>();

        for(String permission : permissions){
            if(checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }

        if (isAskedPermission) {  //已经询问过
            permissionListener.onGranted();
        } else {  //未询问过
            if(!deniedPermissions.isEmpty()) {
                requestPermissions(deniedPermissions.toArray(new String[deniedPermissions.size()]), PERMISSION_REQUESTCODE);
            }else {
                //全部权限都授权
                permissionListener.onGranted();
            }
            isAskedPermission = true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case PERMISSION_REQUESTCODE:
                if(grantResults.length > 0) {
                    handlePermissionResult(permissions,grantResults);
                }
            break;
        }
    }

    private void handlePermissionResult(String[] permissions, int[] grantResults) {
        //存放没授权的权限
        List<String> deniedPermissions = new ArrayList<>();

        for(int i = 0; i < grantResults.length; i++) {
            int grantResult = grantResults[i];
            String permission = permissions[i];
            if(grantResult != PackageManager.PERMISSION_GRANTED) {
                deniedPermissions.add(permission);
            }
        }

        if(deniedPermissions.isEmpty()) {
            permissionListener.onGranted();
        }else {
            permissionListener.onDenied(deniedPermissions);
        }
    }

    public interface PermissionListener {
        void onGranted();

        void onDenied(List<String> deniedPermissions);
    }

}
