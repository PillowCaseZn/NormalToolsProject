package com.pillowcase.normal.tools.permission;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;

import androidx.core.content.PermissionChecker;

import com.pillowcase.normal.tools.permission.impl.IPermissionRequestCallback;
import com.pillowcase.normal.tools.permission.ui.PermissionRequestActivity;

import java.util.ArrayList;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-22 14:18
 * Description :
 */
public class PermissionUtils {
    private static PermissionUtils instance;
    private IPermissionRequestCallback mCallback;

    public static PermissionUtils getInstance() {
        if (instance == null) {
            synchronized (PermissionUtils.class) {
                if (instance == null) {
                    instance = new PermissionUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 动态请求权限
     */
    public void requestPermission(Activity activity, IPermissionRequestCallback callback, String... permissions) {
        if (permissions == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> requestPermissionList = new ArrayList<>();

            //首先检查权限是否已经授权了
            if (permissions.length == 1) {
                if (!checkPermission(activity, permissions[0])) {
                    requestPermissionList.add(permissions[0]);
                }
            } else {
                for (String s : permissions) {
                    if (!checkPermission(activity, s)) {
                        requestPermissionList.add(s);
                    }
                }
            }
            if (requestPermissionList.size() != 0) {
                mCallback = callback;

                Intent intent = new Intent(activity, PermissionRequestActivity.class);
                intent.putStringArrayListExtra("RequestPermission", requestPermissionList);
                activity.startActivity(intent);
                activity.overridePendingTransition(0, 0);
            } else {
                callback.allGranted();
            }
        } else {
            callback.allGranted();
        }
    }

    public IPermissionRequestCallback getCallback() {
        return mCallback;
    }

    private boolean checkPermission(Activity activity, String permission) {
        return PermissionChecker.checkSelfPermission(activity, permission) == PermissionChecker.PERMISSION_GRANTED;
    }
}
