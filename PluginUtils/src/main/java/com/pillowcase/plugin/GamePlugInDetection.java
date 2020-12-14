package com.pillowcase.plugin;

import android.content.Context;
import android.text.TextUtils;

import com.pillowcase.plugin.interfaces.IGamePlugInDetectionCallback;
import com.pillowcase.plugin.interfaces.assets.ITextFileListener;
import com.pillowcase.plugin.modules.App;
import com.pillowcase.plugin.utils.AssetsUtils;
import com.pillowcase.plugin.utils.InstalledAppUtils;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 13:52
 * Description ：游戏外挂检测
 */
public class GamePlugInDetection {
    private static GamePlugInDetection instance;
    private Context mContext;
    private IGamePlugInDetectionCallback mCallback;

    /**
     * 配置文件中的配置的游戏外挂信息
     */
    private List<App> mPluginConfigList;
    /**
     * 监测到设备安装的游戏外挂信息
     */
    private List<App> mInstallPluginList;

    public GamePlugInDetection() {

    }

    public static GamePlugInDetection getInstance() {
        synchronized (GamePlugInDetection.class) {
            if (instance == null) {
                instance = new GamePlugInDetection();
            }
        }
        return instance;
    }

    /**
     * 调用检测接口
     */
    public void detection(Context context, IGamePlugInDetectionCallback callback) {
        try {
            this.mContext = context;
            this.mCallback = callback;

            mPluginConfigList = new ArrayList<>();
            mInstallPluginList = new ArrayList<>();

            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 加载配置文件信息
     */
    private void loadData() {
        String fileFolder = "agentres";
        String fileName = "GamePlugin.json";

        final String APP_NAME = "AppName";
        final String PACKAGE_NAME = "PackageName";

        AssetsUtils.loadTextFile(this.mContext, fileFolder, fileName, new ITextFileListener() {
            @Override
            public void onTextFileResult(String data) {
                try {
                    if (!TextUtils.isEmpty(data)) {
                        if (data.startsWith("[") && data.endsWith("]")) {
                            JSONArray array = new JSONArray(data);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);
                                App plugin = new App();
                                if (object.has(APP_NAME)) {
                                    plugin.setAppName(object.getString(APP_NAME));
                                }
                                if (object.has(PACKAGE_NAME)) {
                                    plugin.setPackageName(object.getString(PACKAGE_NAME));
                                }
                                mPluginConfigList.add(plugin);
                            }
                        }
                    }
                    if (mPluginConfigList != null && mPluginConfigList.size() > 0) {
                        loadInstallApp();
                    }
                    detection();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 检测判断
     */
    private void detection() {
        try {
            //检测包体是否已经损坏
            boolean isPresent = findClassFile("com.gh.shell.FakeApplication");
            if (isPresent) {
                this.mCallback.onDamage();
            } else {
                if (this.mInstallPluginList.size() == 0) {
                    this.mCallback.onResult(false, new ArrayList<>());
                } else {
                    this.mCallback.onResult(true, this.mInstallPluginList);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean findClassFile(String name) {
        try {
            Class.forName(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void loadInstallApp() {
        try {
            List<App> installAppInfo = InstalledAppUtils.getInstalledAppList(this.mContext);

            for (App installApp : installAppInfo) {
                App plugin = new App();
                plugin.setAppName(installApp.getAppName());
                plugin.setPackageName(installApp.getPackageName());
                if (mPluginConfigList.contains(plugin)) {
                    plugin.setApplicationName(installApp.getApplicationName());
                    plugin.setRunning(installApp.isRunning());
                    if (!mInstallPluginList.contains(plugin)) {
                        mInstallPluginList.add(plugin);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
