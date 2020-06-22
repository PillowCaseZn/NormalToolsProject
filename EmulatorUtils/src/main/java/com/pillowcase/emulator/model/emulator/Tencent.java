package com.pillowcase.emulator.model.emulator;

import android.app.ActivityManager;

import com.pillowcase.emulator.model.Device;
import com.pillowcase.logger.LoggerUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 腾讯手游模拟器
 */
public class Tencent {
    private static LoggerUtils mLoggerUtils;

    public Tencent() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    public static JSONObject isEmulator(Device deviceInfo, List<ActivityManager.RunningAppProcessInfo> processList) {
        JSONObject object = new JSONObject();
        try {
            log("isEmulator", "");

            for (ActivityManager.RunningAppProcessInfo info : processList) {
                if (info.processName.equals("com.tencent.tinput")) {
                    object.put("isEmulator", true);
                    object.put("Emulator Name", "腾讯手游模拟器");
                    object.put("Emulator", info);
                }
            }
            //设备品牌(Brand):tencent
            //唯一标识(FingerPrint):tencent/vbox .. test-keys
            if (deviceInfo.getBrand().equals("tencent") &&
                    (deviceInfo.getFingerPrint().contains("tencent") || deviceInfo.getFingerPrint().contains("vbox") || deviceInfo.getFingerPrint().contains("test-keys"))) {
                object.put("设备品牌(Brand)", deviceInfo.getBrand());
                object.put("唯一标识(FingerPrint)", deviceInfo.getFingerPrint());
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
