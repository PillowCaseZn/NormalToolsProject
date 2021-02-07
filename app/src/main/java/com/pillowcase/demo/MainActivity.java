package com.pillowcase.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pillowcase.logger.PillowLogger;
import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.utils.AssetsUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private String[] data = new String[]{
            "1",
            "2",
            "3",
    };

    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        try {
            log("onCreate - data", data);
//            PluginLog.log("onCreate", data);

            AppBean[] one = new AppBean[]{
                    new AppBean("万能加速器", "com.gh.universalaccelerator"),
                    new AppBean("万能加速器", "com.gh.universalacceleratorcom.gh.universalacceleratorcom.gh.universalaccelerator")
            };
            log("onCreate - AppBean", one);

            List<AppBean> mPluginAppList = new ArrayList<>();
            mPluginAppList.add(new AppBean("万能加速器", "com.gh.universalaccelerator"));
            mPluginAppList.add(new AppBean("游戏蜂窝", "com.cyjh.gundam"));
            mPluginAppList.add(new AppBean("八门神器", "com.zhangkongapp.joke.bamenshenqi"));
            log("onCreate - PluginAppList", mPluginAppList);
//            PluginLog.log("onCreate", mPluginAppList);

            Object data = AssetsUtils.getInstance().loadData(this, AssetsUtils.Type.File, "GamePlugin.json");
            log("onCreate - assets", data);
            log("onCreate - assets", data + "123");
            log("onCreate - assets", "123");

            AppBean bean = new AppBean("万能加速器", "com.gh.universalaccelerator");
            log("onCreate - bean", bean);

            JSONObject object = new JSONObject("{\n" +
                    "\"AppName\": \"万能加速器\",\n" +
                    "\"PackageName\": \"com.gh.universalaccelerator\"\n" +
                    "}");
            log("onCreate - object", object);
            log("onCreate - object", new JSONObject());

            JSONArray array = new JSONArray();
            array.put(object);
            log("onCreate - array", array);
            array.put(object);
            log("onCreate - array", array);

            Map<String, Integer> map = new HashMap<>();
            map.put("1", 1);
            map.put("2", 2);
            log("onCreate - map", map);

            Map<String, AppBean> dataMap = new HashMap<>();
            dataMap.put("1", new AppBean("万能加速器", "com.gh.universalaccelerator"));
            log("onCreate - dataMap", dataMap);

            Map<Integer, AppBean> dataMap2 = new HashMap<>();
            dataMap2.put(1, new AppBean("万能加速器", "com.gh.universalaccelerator"));
            log("onCreate - dataMap2", dataMap2);

            Map<Float, AppBean> dataMap3 = new HashMap<>();
            dataMap3.put(1f, new AppBean("万能加速器", "com.gh.universalaccelerator"));
            log("onCreate - dataMap3", dataMap3);

        } catch (Exception e) {
            error(e, "onCreate");
        }
    }

    @Override
    protected void onPause() {
        log("onPause", "");
        super.onPause();
    }

    @Override
    protected void onResume() {
        log("onResume", "");
        super.onResume();
    }

    public void log(String method, Object object) {
        PillowLogger.getInstance().log(method, object);
    }

    public void error(Exception exception, String method) {
        exception.printStackTrace();
        PillowLogger.getInstance().error(method, exception);
    }
}
