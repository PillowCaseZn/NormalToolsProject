package com.pillowcase.emulator.model.emulator;

import com.pillowcase.emulator.model.AppInfo;
import com.pillowcase.logger.LoggerUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 逍遥模拟器
 */
public class Microvirt {
    private static LoggerUtils mLoggerUtils;

    public Microvirt() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    public static JSONObject isEmulator(List<AppInfo> appList) {
        JSONObject object = new JSONObject();
        try {
            log("isEmulator", "");

            for (AppInfo info : appList) {
                if (info.getLabel().equals("逍遥市场") && info.getPackageName().equals("com.microvirt.market")) {
                    object.put("isEmulator", true);
                    object.put("Emulator Name", "逍遥模拟器");
                    object.put("Emulator", info);
                }
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
