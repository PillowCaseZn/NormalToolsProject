package com.pillowcase.plugin.simulator;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.Constant;
import com.pillowcase.plugin.modules.DeviceBean;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 夜神模拟器
 */
public class BignNoxSimulator {
    public static JSONObject isSimulator(DeviceBean deviceBeanInfo, List<AppBean> appBeanList) {
        JSONObject object = new JSONObject();
        try {
            for (AppBean info : appBeanList) {
                if (info.getLabel().equals("游戏中心") && info.getPackageName().equals("com.bignox.app.store.hd")) {
                    object.put(Constant.Simulator.IS_SIMULATOR, true);
                    object.put(Constant.Simulator.SIMULATOR_NAME, "夜神模拟器");
                    object.put(Constant.Simulator.SIMULATOR_INFO, info);
                }
                if (info.getPackageName().equals("com.vphone.launcher")) {
                    object.put("PackageName", info);
                }
            }
            //处理器信息(Board):shamu
            //渠道信息(Flavor):aosp_shamu-user
            if (deviceBeanInfo.getBoard().equals("shamu") && deviceBeanInfo.getFlavor().equals("aosp_shamu-user")) {
                object.put("处理器信息(Board)", deviceBeanInfo.getBoard());
                object.put("渠道信息(Flavor)", deviceBeanInfo.getFlavor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
