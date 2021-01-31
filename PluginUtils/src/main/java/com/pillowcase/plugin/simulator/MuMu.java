package com.pillowcase.plugin.simulator;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.modules.Constant;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： MuMu模拟器
 */
public class MuMu {
    public static JSONObject isSimulator(DeviceBean deviceBeanInfo, List<AppBean> appBeanList) {
        JSONObject object = new JSONObject();
        try {
            for (AppBean info : appBeanList) {
                if (info.getLabel().equals("应用中心") && info.getPackageName().equals("com.mumu.store")) {
                    object.put(Constant.Simulator.IS_SIMULATOR, true);
                    object.put(Constant.Simulator.SIMULATOR_NAME, "MuMu模拟器");
                    object.put(Constant.Simulator.SIMULATOR_INFO, info);
                }
                if (info.getLabel().equals("多开助手") && info.getPackageName().equals("com.netease.mumu.cloner")) {
                    object.put("Package", info);
                }
            }
            //手机的型号(Model):MuMu
            //渠道信息(Flavor):cancro-user
            if (deviceBeanInfo.getModel().equals("MuMu") && deviceBeanInfo.getFlavor().equals("cancro-user")) {
                object.put("处理器信息(Board)", deviceBeanInfo.getModel());
                object.put("渠道信息(Flavor)", deviceBeanInfo.getFlavor());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
