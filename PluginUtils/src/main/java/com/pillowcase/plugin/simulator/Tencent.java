package com.pillowcase.plugin.simulator;

import android.app.ActivityManager;

import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.modules.Constant;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 腾讯手游模拟器
 */
public class Tencent {
    public static JSONObject isSimulator(DeviceBean deviceBeanInfo, List<ActivityManager.RunningAppProcessInfo> processList) {
        JSONObject object = new JSONObject();
        try {
            for (ActivityManager.RunningAppProcessInfo info : processList) {
                if (info.processName.equals("com.tencent.tinput")) {
                    object.put(Constant.Simulator.IS_SIMULATOR, true);
                    object.put(Constant.Simulator.SIMULATOR_NAME, "腾讯手游模拟器");
                    object.put(Constant.Simulator.SIMULATOR_INFO, info);
                }
            }
            //设备品牌(Brand):tencent
            //唯一标识(FingerPrint):tencent/vbox .. test-keys
            if (deviceBeanInfo.getBrand().equals("tencent") &&
                    (deviceBeanInfo.getFingerPrint().contains("tencent") || deviceBeanInfo.getFingerPrint().contains("vbox") || deviceBeanInfo.getFingerPrint().contains("test-keys"))) {
                object.put("设备品牌(Brand)", deviceBeanInfo.getBrand());
                object.put("唯一标识(FingerPrint)", deviceBeanInfo.getFingerPrint());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
