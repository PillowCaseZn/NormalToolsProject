package com.pillowcase.plugin.simulator;

import android.app.ActivityManager;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.Constant;
import com.pillowcase.plugin.modules.DeviceBean;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 15:21
 * Description ：
 */
public class FlySilkWorm {
    public static JSONObject isSimulator(DeviceBean deviceBeanInfo, List<ActivityManager.RunningAppProcessInfo> processList, List<AppBean> appBeanList) {
        JSONObject object = new JSONObject();
        try {
            for (AppBean info : appBeanList) {
                if (info.getLabel().equals("雷电游戏中心") && info.getPackageName().equals("com.android.flysilkworm")) {
                    object.put(Constant.Simulator.IS_SIMULATOR, true);
                    object.put(Constant.Simulator.SIMULATOR_NAME, "雷电模拟器");
                    object.put(Constant.Simulator.SIMULATOR_INFO, info);
                }
            }

            //主板平台(Platform):aosp-user
            //渠道信息(Flavor):aosp-user
            if (deviceBeanInfo.getPlatform().equals("aosp-user") && deviceBeanInfo.getFlavor().equals("aosp-user")) {
                object.put("主板平台(Platform)", deviceBeanInfo.getPlatform());
                object.put("渠道信息(Flavor)", deviceBeanInfo.getFlavor());
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
