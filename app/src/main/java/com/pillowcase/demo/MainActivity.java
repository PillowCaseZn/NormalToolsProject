package com.pillowcase.demo;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.pillowcase.logger.PillowLogger;
import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.utils.AssetsUtils;
import com.pillowcase.plugin.utils.PluginLog;

import java.util.ArrayList;
import java.util.List;

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
            log("onCreate", data);
//            PluginLog.log("onCreate", data);

            List<AppBean> mPluginAppList = new ArrayList<>();
            mPluginAppList.add(new AppBean("万能加速器", "com.gh.universalaccelerator"));
            mPluginAppList.add(new AppBean("游戏蜂窝", "com.cyjh.gundam"));
            mPluginAppList.add(new AppBean("八门神器", "com.zhangkongapp.joke.bamenshenqi"));
            log("onCreate", mPluginAppList);

            Object data = AssetsUtils.getInstance().loadData(this, AssetsUtils.Type.File, "GamePlugin.json");
            log("onCreate", data);

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

    public void warn(String method, String message) {
    }

    public void error(Exception exception, String method) {
        PillowLogger.getInstance().error(method, exception);
    }
}
