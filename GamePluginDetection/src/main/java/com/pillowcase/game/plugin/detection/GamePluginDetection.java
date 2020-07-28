package com.pillowcase.game.plugin.detection;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;

import com.pillowcase.game.plugin.detection.modules.GamePlugin;
import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.logger.impl.ILoggerOperation;
import com.pillowcase.utils.AssetsUtils;
import com.pillowcase.utils.interfaces.IAssetsListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
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
    private List<GamePlugin> mGamePluginList = new ArrayList<>();

    private AssetsUtils mAssetsUtils = new AssetsUtils(new IAssetsListener() {
        @Override
        public void TextFileResult(String data) {
            try {
                log("TextFileResult", "Data : " + data);
                if (data.startsWith("[") && data.endsWith("]")) {
                    JSONArray array = new JSONArray(data);
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject object = array.optJSONObject(i);
                        log("TextFileResult", "JsonObject : " + object);
                        GamePlugin plugin = new GamePlugin();
                        if (object.has(APP_NAME)) {
                            plugin.setAppName(object.getString(APP_NAME));
                        }
                        if (object.has(PACKAGE_NAME)) {
                            plugin.setPackageName(object.getString(PACKAGE_NAME));
                        }
                        mGamePluginList.add(plugin);
                    }
                }
                log("TextFileResult", "GamePluginList : " + mGamePluginList);
                loadInstallAppConfig();
            } catch (Exception e) {
                error(e, "TextFileResult");
            }
        }

        @Override
        public void ImageFileResult(Bitmap bitmap) {

        }

        @Override
        public void VideoFileResult(AssetFileDescriptor fileDescriptor) {

        }
    });

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
    public void detection(Context context) {
        try {
            log("detection", "");
            this.mContext = context;

            //读取外挂APP相关信息
            mAssetsUtils.loadTextFile(this.mContext, "Data", "GamePlugin.json");
        } catch (Exception e) {
            error(e, "detection");
        }
    }

    /**
     * 查看设备已安装APP信息
     */
    private void loadInstallAppConfig() {
        try {
            log("loadInstallAppConfig", "");
            PackageManager manager = this.mContext.getPackageManager();
            if (manager != null) {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfoList = manager.queryIntentActivities(intent, 0);
                Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(manager));

                for (ResolveInfo info : resolveInfoList) {
                    String label = (String) info.loadLabel(manager); // 获得应用程序的Label
                    String packageName = info.activityInfo.packageName; // 获得应用程序的包名
                    log("loadInstallAppConfig", "ResolveInfo : " + info.resolvePackageName);
                    log("loadInstallAppConfig", "ResolveInfo : " + packageName);
                    log("loadInstallAppConfig", "ResolveInfo : " + label);
                }
            }
        } catch (Exception e) {
            error(e, "loadInstallAppConfig");
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
