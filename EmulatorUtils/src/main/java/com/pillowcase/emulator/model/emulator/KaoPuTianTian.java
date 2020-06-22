package com.pillowcase.emulator.model.emulator;

import com.pillowcase.emulator.model.AppInfo;
import com.pillowcase.logger.LoggerUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 靠谱/天天模拟器
 */
public class KaoPuTianTian {
    private static LoggerUtils mLoggerUtils;

    public KaoPuTianTian() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    public static JSONObject isEmulator(List<AppInfo> appList) {
        JSONObject object = new JSONObject();
        try {
            log("isEmulator", "");
            List<String> nameList = new ArrayList<>();

            for (AppInfo info : appList) {
                nameList.add(info.getPackageName());
            }
            if (nameList.contains("com.tiantian.ime") && nameList.contains("com.kaopu.googleinstaller")) {
                object.put("isEmulator", true);
                object.put("Emulator Name", "靠谱/天天模拟器");
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
