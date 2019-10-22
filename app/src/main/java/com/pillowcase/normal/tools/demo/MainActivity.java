package com.pillowcase.normal.tools.demo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pillowcase.normal.tools.emulator.EmulatorUtls;
import com.pillowcase.normal.tools.logger.LoggerTools;
import com.pillowcase.normal.tools.logger.base.BaseLogger;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;
import com.pillowcase.normal.tools.permission.PermissionUtils;
import com.pillowcase.normal.tools.permission.impl.IPermissionRequestCallback;
import com.pillowcase.normal.tools.permission.model.Permission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements ILoggerOperation {
    private LoggerTools loggerTools;
    private String[] data = new String[]{
            "1",
            "2",
            "3",
    };

    private MainLogger mainLogger;
    private BaseLogger base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loggerTools = new LoggerTools();
        loggerTools.init("Test", false);
        mainLogger = new MainLogger(true, "MainLogger");
        base = new BaseLogger(false, "Base");

        log(getClass().getSimpleName(), "12312313\nqeweqwe");
        log(getClass().getSimpleName(), data);
        log(getClass().getSimpleName(), null);
        log(getClass().getSimpleName(), "");
        log(getClass().getSimpleName(), EmulatorUtls.isEmulator(this));

        try {
            JSONObject object = new JSONObject();
            object.put("data", "1");
            object.put("data2", Arrays.toString(data));

            log("Json", object);
            Uri uri = Uri.parse("content://com.player.sdk.provider/Account");
            log("Test", "Type : " + getContentResolver().getType(uri));
            @SuppressLint("Recycle") Cursor cursor = getContentResolver().query(uri, new String[]{"package_name", "data"},
                    "package_name = ?",
                    new String[]{"com.player.sdk.demo"},
                    null);
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    String packageName = cursor.getString(cursor.getColumnIndex("package_name"));
                    String value = cursor.getString(cursor.getColumnIndex("data"));
                    log("Test", "PackageName : " + packageName + "\nValue : " + value);
                }
            }

            uri = Uri.parse("content://com.player.union.demo.provider/Account");
            log("Test", "Type : " + getContentResolver().getType(uri));

            JSONArray array = object.getJSONArray("data2");

        } catch (JSONException e) {
            error(e, "Test");
        }

        PermissionUtils.getInstance().requestPermission(this, new IPermissionRequestCallback() {
            @Override
            public void allGranted() {
                log(getClass().getSimpleName(), "allGranted");
            }

            @Override
            public void refused(String permission) {
                log(getClass().getSimpleName(), "Permission : " + permission);

            }
        }, Permission.READ_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void log(String method, Object object) {
        loggerTools.log(method, object);
        mainLogger.log(method, object);
        base.log(method, object);
    }

    @Override
    public void warn(String method, String message) {
        loggerTools.warn(method, message);
        mainLogger.warn(method, message);
        base.warn(method, message);
    }

    @Override
    public void error(Throwable throwable, String method) {
        loggerTools.error(throwable, method);
        mainLogger.error(throwable, method);
        base.error(throwable, method);
    }
}
