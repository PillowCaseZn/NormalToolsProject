package com.pillowcase.normal.tools.demo;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pillowcase.normal.tools.emulator.EmulatorUtls;
import com.pillowcase.normal.tools.logger.LoggerUtils;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;
import com.pillowcase.normal.tools.only.sign.OnlySignUtils;
import com.pillowcase.normal.tools.only.sign.impl.ISupportListener;
import com.pillowcase.normal.tools.only.sign.models.ResultParams;
import com.pillowcase.normal.tools.permission.PermissionUtils;
import com.pillowcase.normal.tools.permission.impl.IPermissionRequestCallback;
import com.pillowcase.normal.tools.permission.model.Permission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ILoggerOperation {
    private String[] data = new String[]{
            "1",
            "2",
            "3",
    };

    private LoggerUtils mLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mLogger = new LoggerUtils(true, "fuck");
//        loggerTest.log("\"12312313\\nqeweqwe\"");
        mLogger.log("123", "123");
        mLogger.log("123", "");

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
            warn("Json", object.toString());

            JSONArray array = new JSONArray();
            array.put(0, "2");
            array.put(1, Arrays.toString(data));

            log("array", array);
            warn("array", array.toString());

            DemoModule module = new DemoModule(1, "123");
            log("DemoModule", module);
            warn("DemoModule", module.toString());

            List<DemoModule> dataList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                dataList.add(new DemoModule(i, "I : " + i));
            }
            log("DemoModule List", dataList);
            warn("DemoModule List", dataList.toString());

            List<String> l = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
                l.add("I : " + i);
            }
            log("List", l);
            warn("List", l.toString());


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

            array = object.getJSONArray("data2");

        } catch (JSONException e) {
            error(e, "Test");
        }

        PermissionUtils.getInstance().requestPermission(this, new IPermissionRequestCallback() {
            @Override
            public void allGranted() {
                log(getClass().getSimpleName(), "allGranted");


                OnlySignUtils.getInstance().getOnlySign(MainActivity.this, new ISupportListener() {
                    @Override
                    public void result(ResultParams data) {
                        log("Test-OnlySign", "Data : "+data);
                    }
                });
            }

            @Override
            public void refused(String permission) {
                log(getClass().getSimpleName(), "Permission : " + permission);

            }
        }, Permission.READ_EXTERNAL_STORAGE, Permission.READ_PHONE_STATE, Permission.WRITE_EXTERNAL_STORAGE);
    }

    @Override
    public void log(String method, Object object) {
        mLogger.log(method, object);
    }

    @Override
    public void warn(String method, String message) {
        mLogger.warn(method, message);
    }

    @Override
    public void error(Throwable throwable, String method) {
        mLogger.error(throwable, method);
    }
}
