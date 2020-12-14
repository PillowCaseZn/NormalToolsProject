package com.pillowcase.plugin.utils.simulator;

import android.app.ActivityManager;

import com.pillowcase.plugin.modules.Device;
import com.pillowcase.plugin.modules.SimulatorConstant;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 腾讯手游模拟器
 */
public class Tencent {
    public static JSONObject isSimulator(Device deviceInfo, List<ActivityManager.RunningAppProcessInfo> processList) {
        JSONObject object = new JSONObject();
        try {
            for (ActivityManager.RunningAppProcessInfo info : processList) {
                if (info.processName.equals("com.tencent.tinput")) {
                    object.put(SimulatorConstant.IS_SIMULATOR, true);
                    object.put(SimulatorConstant.SIMULATOR_NAME, "腾讯手游模拟器");
                    object.put(SimulatorConstant.SIMULATOR_INFO, info);
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
            e.printStackTrace();
        }
        return object;
    }
}
