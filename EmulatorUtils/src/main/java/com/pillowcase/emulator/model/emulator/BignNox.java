package com.pillowcase.emulator.model.emulator;

import com.pillowcase.emulator.model.AppInfo;
import com.pillowcase.emulator.model.Device;
import com.pillowcase.logger.LoggerUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 夜神模拟器
 */
public class BignNox {
    private static LoggerUtils mLoggerUtils;

    public BignNox() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    public static JSONObject isEmulator(Device deviceInfo, List<AppInfo> appList) {
        JSONObject object = new JSONObject();
        try {
            log("isEmulator", "");

            for (AppInfo info : appList) {
                if (info.getLabel().equals("游戏中心") && info.getPackageName().equals("com.bignox.app.store.hd")) {
                    object.put("isEmulator", true);
                    object.put("Emulator Name", "夜神模拟器");
                    object.put("Emulator", info);
                }
                if (info.getPackageName().equals("com.vphone.launcher")) {
                    object.put("PackageName", info);
                }
            }
            //处理器信息(Board):shamu
            //渠道信息(Flavor):aosp_shamu-user
            if (deviceInfo.getBoard().equals("shamu") && deviceInfo.getFlavor().equals("aosp_shamu-user")) {
                object.put("处理器信息(Board)", deviceInfo.getBoard());
                object.put("渠道信息(Flavor)", deviceInfo.getFlavor());
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
