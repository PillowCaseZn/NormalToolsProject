package com.pillowcase.emulator.model.emulator;

import com.pillowcase.emulator.model.AppInfo;
import com.pillowcase.logger.LoggerUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 蓝叠模拟器
 */
public class BlueStacks {
    private static LoggerUtils mLoggerUtils;

    public BlueStacks() {
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
            int suspected = 0;
            if (nameList.contains("com.bluestacks.appfinder")) {
                suspected++;
            }
            if (nameList.contains("com.bluestacks.appmark")) {
                suspected++;
            }
            if (nameList.contains("com.bluestacks.setting")) {
                suspected++;
            }
            if (nameList.contains("com.bluestacks.searchapp")) {
                suspected++;
            }
            if (nameList.contains("com.bluestacks.setup")) {
                suspected++;
            }
            log("isEmulator", "Suspected : " + suspected);
            if (suspected >= 2) {
                object.put("isEmulator", true);
                object.put("Emulator Name", "蓝叠模拟器");
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
