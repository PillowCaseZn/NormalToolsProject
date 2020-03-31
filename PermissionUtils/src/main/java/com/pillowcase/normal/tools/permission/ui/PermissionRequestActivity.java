package com.pillowcase.normal.tools.permission.ui;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.pillowcase.normal.tools.logger.LoggerUtils;
import com.pillowcase.normal.tools.permission.PermissionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/**
 * Author      : PillowCase
 * Create On   : 2019-10-22 15:02
 * Description :
 */
public class PermissionRequestActivity extends Activity {
    private ArrayList<String> requestPermission;
    private int PERMISSION_REQUEST_CODE = 0x101;
    private LoggerUtils mLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLogger = new LoggerUtils(true, "PermissionUtils");

        requestPermission = getIntent().getStringArrayListExtra("RequestPermission");
        mLogger.log("", "Request Permission : \n" + requestPermission);

        String[] data = requestPermission.toArray(new String[0]);
        mLogger.log("", "Data : \n" + Arrays.toString(data));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(data, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        mLogger.log("", "RequestCode : " + requestCode
                + "\nPermissions : " + Arrays.toString(permissions)
                + "\ngrantResults : " + Arrays.toString(grantResults));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (requestCode == PERMISSION_REQUEST_CODE) {
                Iterator<String> iterator = requestPermission.iterator();
                while (iterator.hasNext()) {
                    if (checkSelfPermission(iterator.next()) == PackageManager.PERMISSION_GRANTED) {
                        iterator.remove();
                    }
                }

                if (requestPermission.size() == 0) {
                    PermissionUtils.getInstance().getCallback().allGranted();
                } else {
                    for (String permission : requestPermission) {
                        PermissionUtils.getInstance().getCallback().refused(permission);
                    }
                }
                PermissionRequestActivity.this.finish();
                overridePendingTransition(0, 0);
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
