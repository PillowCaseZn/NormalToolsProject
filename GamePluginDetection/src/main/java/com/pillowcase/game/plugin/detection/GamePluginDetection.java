package com.pillowcase.game.plugin.detection;

import android.annotation.SuppressLint;
import android.content.Context;

import com.pillowcase.game.plugin.detection.impl.IDetectionCallback;
import com.pillowcase.game.plugin.detection.modules.GamePlugin;
import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.logger.impl.ILoggerOperation;
import com.pillowcase.utils.AppInstallUtils;
import com.pillowcase.utils.AssetsUtils;
import com.pillowcase.utils.interfaces.assets.IAssetsTextFileListener;
import com.pillowcase.utils.modules.InstallApp;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-07-28 14:58
 * Description ： 游戏外挂检测类
 */
public class GamePluginDetection implements ILoggerOperation {
    private static final GamePluginDetection ourInstance = new GamePluginDetection();

    private static final String APP_NAME = "AppName";
    private static final String PACKAGE_NAME = "PackageName";

    private LoggerUtils mLoggerUtils;
    private Context mContext;
    private IDetectionCallback mCallback;
    /**
     * 配置文件中的配置的游戏外挂信息
     */
    private List<GamePlugin> mPluginConfigList;
    /**
     * 监测到设备安装的游戏外挂信息
     */
    private List<GamePlugin> mInstallPluginList;

    public static GamePluginDetection getInstance() {
        return ourInstance;
    }

    private GamePluginDetection() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    /**
     * 检测
     */
    public void detection(Context context, IDetectionCallback callback) {
        try {
            log("detection", "");
            this.mContext = context;
            this.mCallback = callback;

            mPluginConfigList = new ArrayList<>();
            mInstallPluginList = new ArrayList<>();

            //读取外挂APP相关信息
            AssetsUtils.getInstance().loadTextFile(this.mContext, "agentres", "GamePlugin.json", new IAssetsTextFileListener() {
                @Override
                public void TextFileResult(String data) {
                    try {
//                        log("TextFileResult", "Data : " + data);
                        if (data.startsWith("[") && data.endsWith("]")) {
                            JSONArray array = new JSONArray(data);
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.optJSONObject(i);
//                                log("TextFileResult", "JsonObject : " + object);
                                GamePlugin plugin = new GamePlugin();
                                if (object.has(APP_NAME)) {
                                    plugin.setAppName(object.getString(APP_NAME));
                                }
                                if (object.has(PACKAGE_NAME)) {
                                    plugin.setPackageName(object.getString(PACKAGE_NAME));
                                }
                                mPluginConfigList.add(plugin);
                            }
                        }
//                        log("TextFileResult", "GamePluginList : " + mPluginConfigList);
                        if (mPluginConfigList != null && mPluginConfigList.size() > 0) {
                            loadInstallApp();
                        }
                        check();
                    } catch (Exception e) {
                        error(e, "TextFileResult");
                    }
                }
            });
        } catch (Exception e) {
            error(e, "detection");
        }
    }

    /**
     * 进行判断检测
     */
    @SuppressLint("NewApi")
    private void check() {
        try {
            //检测包体是否已经损坏
            boolean isPresent = findClassFile("com.gh.shell.FakeApplication");
            log("check", "isPresent : " + isPresent);
            if (isPresent) {
                mCallback.onDamage();
            } else {
                if (mInstallPluginList.size() == 0) {
                    mCallback.onResult(false, new ArrayList<>());
                } else {
                    mCallback.onResult(true, mInstallPluginList);
                }
            }
        } catch (Exception e) {
            error(e, "check");
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

    /**
     * 查看设备已安装APP信息
     */
    private void loadInstallApp() {
        try {
            List<InstallApp> installAppInfo = AppInstallUtils.getInstance().getInstallAppInfo(this.mContext);

            for (InstallApp installApp : installAppInfo) {
                GamePlugin plugin = new GamePlugin();
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
//            log("loadInstallApp", "InstallPluginList : " + mInstallPluginList);
        } catch (Exception e) {
            error(e, "loadInstallApp");
        }
    }

    @Override
    public void log(String method, Object object) {
        if (mLoggerUtils != null) {
            mLoggerUtils.log(method, object);
        }
    }

    @Override
    public void warn(String method, String message) {
        if (mLoggerUtils != null) {
            mLoggerUtils.warn(method, message);
        }
    }

    @Override
    public void error(Throwable throwable, String method) {
        if (mLoggerUtils != null) {
            mLoggerUtils.error(throwable, method);
        }
    }
}
