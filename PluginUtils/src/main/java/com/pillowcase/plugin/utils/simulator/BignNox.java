package com.pillowcase.plugin.utils.simulator;

import com.pillowcase.plugin.modules.App;
import com.pillowcase.plugin.modules.Device;
import com.pillowcase.plugin.modules.SimulatorConstant;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 夜神模拟器
 */
public class BignNox {
    public static JSONObject isSimulator(Device deviceInfo, List<App> appList) {
        JSONObject object = new JSONObject();
        try {
            for (App info : appList) {
                if (info.getLabel().equals("游戏中心") && info.getPackageName().equals("com.bignox.app.store.hd")) {
                    object.put(SimulatorConstant.IS_SIMULATOR, true);
                    object.put(SimulatorConstant.SIMULATOR_NAME, "夜神模拟器");
                    object.put(SimulatorConstant.SIMULATOR_INFO, info);
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
            e.printStackTrace();
        }
        return object;
    }
}
