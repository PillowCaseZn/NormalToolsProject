package com.pillowcase.emulator.model.emulator;

import android.app.ActivityManager;
import android.content.pm.ResolveInfo;

import com.pillowcase.emulator.model.AppInfo;
import com.pillowcase.emulator.model.Device;
import com.pillowcase.logger.LoggerUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 雷电模拟器
 */
public class FlySilkWorm {
    private static LoggerUtils mLoggerUtils;

    public FlySilkWorm() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    public static JSONObject isEmulator(Device deviceInfo, List<ActivityManager.RunningAppProcessInfo> processList, List<AppInfo> appList) {
        JSONObject object = new JSONObject();
        try {
            log("isEmulator", "");
            for(AppInfo info : appList){
                if (info.getLabel().equals("雷电游戏中心") && info.getPackageName().equals("com.android.flysilkworm")) {
                    object.put("isEmulator" , true);
                    object.put("Emulator Name" , "雷电模拟器");
                    object.put("Emulator" , info);
                }
            }

            //主板平台(Platform):aosp-user
            //渠道信息(Flavor):aosp-user
            if (deviceInfo.getPlatform().equals("aosp-user") && deviceInfo.getFlavor().equals("aosp-user")) {
                object.put("主板平台(Platform)", deviceInfo.getPlatform());
                object.put("渠道信息(Flavor)", deviceInfo.getFlavor());
            }
            if (processList.contains("com.android.flysilkworm")) {
                object.put("运行进程", "com.android.flysilkworm");
            }
        } catch (Exception e) {
            error(e, "isEmulator");
        }
        return object;
    }

    private static void log(String method, Object object) {
        if (mLoggerUtils != null) {
            mLoggerUtils.log(method, object);
        }
    }

    private static void warn(String method, String message) {
        if (mLoggerUtils != null) {
            mLoggerUtils.warn(method, message);
        }
    }

    private static void error(Throwable throwable, String method) {
        if (mLoggerUtils != null) {
            mLoggerUtils.error(throwable, method);
        }
    }
}
