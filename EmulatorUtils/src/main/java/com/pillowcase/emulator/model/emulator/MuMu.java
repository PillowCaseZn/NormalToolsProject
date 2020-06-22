package com.pillowcase.emulator.model.emulator;

import com.pillowcase.emulator.model.AppInfo;
import com.pillowcase.emulator.model.Device;
import com.pillowcase.logger.LoggerUtils;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： MuMu模拟器
 */
public class MuMu {
    private static LoggerUtils mLoggerUtils;

    public MuMu() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    public static JSONObject isEmulator(Device deviceInfo, List<AppInfo> appList) {
        JSONObject object = new JSONObject();
        try {
            log("isEmulator", "");

            for(AppInfo info : appList){
                if (info.getLabel().equals("应用中心") && info.getPackageName().equals("com.mumu.store")) {
                    object.put("isEmulator" , true);
                    object.put("Emulator Name" , "MuMu模拟器");
                    object.put("Emulator" , info);
                }
            }
            //手机的型号(Model):MuMu
            //渠道信息(Flavor):cancro-user
            if (deviceInfo.getModel().equals("MuMu")&&deviceInfo.getFlavor().equals("cancro-user")) {
                object.put("处理器信息(Board)", deviceInfo.getModel());
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
