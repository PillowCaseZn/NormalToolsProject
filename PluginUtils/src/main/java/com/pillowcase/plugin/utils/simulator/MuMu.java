package com.pillowcase.plugin.utils.simulator;

import com.pillowcase.plugin.modules.App;
import com.pillowcase.plugin.modules.Device;
import com.pillowcase.plugin.modules.SimulatorConstant;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： MuMu模拟器
 */
public class MuMu {
    public static JSONObject isSimulator(Device deviceInfo, List<App> appList) {
        JSONObject object = new JSONObject();
        try {
            for (App info : appList) {
                if (info.getLabel().equals("应用中心") && info.getPackageName().equals("com.mumu.store")) {
                    object.put(SimulatorConstant.IS_SIMULATOR, true);
                    object.put(SimulatorConstant.SIMULATOR_NAME, "MuMu模拟器");
                    object.put(SimulatorConstant.SIMULATOR_INFO, info);
                }
                if (info.getLabel().equals("多开助手") && info.getPackageName().equals("com.netease.mumu.cloner")) {
                    object.put("Package", info);
                }
            }
            //手机的型号(Model):MuMu
            //渠道信息(Flavor):cancro-user
            if (deviceInfo.getModel().equals("MuMu") && deviceInfo.getFlavor().equals("cancro-user")) {
                object.put("处理器信息(Board)", deviceInfo.getModel());
                object.put("渠道信息(Flavor)", deviceInfo.getFlavor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
