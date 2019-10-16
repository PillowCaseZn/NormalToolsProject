package com.pillowcase.normal.tools.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pillowcase.normal.tools.logger.LoggerTools;
import com.pillowcase.normal.tools.logger.base.BaseLogger;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;

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


        try {
            JSONObject object = new JSONObject();
            object.put("data", "1");
            object.put("data2", Arrays.toString(data));

            log("Json", object);

            JSONArray array = object.getJSONArray("data2");
        } catch (JSONException e) {
            error(e, "Test");
        }
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
