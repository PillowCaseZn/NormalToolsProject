package com.pillowcase.plugin.utils.simulator;

import android.app.ActivityManager;

import com.pillowcase.plugin.modules.App;
import com.pillowcase.plugin.modules.Device;
import com.pillowcase.plugin.modules.SimulatorConstant;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 15:21
 * Description ：
 */
public class FlySilkWorm {
    public static JSONObject isSimulator(Device deviceInfo, List<ActivityManager.RunningAppProcessInfo> processList, List<App> appList) {
        JSONObject object = new JSONObject();
        try {
            for (App info : appList) {
                if (info.getLabel().equals("雷电游戏中心") && info.getPackageName().equals("com.android.flysilkworm")) {
                    object.put(SimulatorConstant.IS_SIMULATOR, true);
                    object.put(SimulatorConstant.SIMULATOR_NAME, "雷电模拟器");
                    object.put(SimulatorConstant.SIMULATOR_INFO, info);
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
            e.printStackTrace();
        }
        return object;
    }
}
