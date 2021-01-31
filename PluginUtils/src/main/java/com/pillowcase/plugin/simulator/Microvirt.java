package com.pillowcase.plugin.simulator;


import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.Constant;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 逍遥模拟器
 */
public class Microvirt {
    public static JSONObject isSimulator(List<AppBean> appBeanList) {
        JSONObject object = new JSONObject();
        try {
            for (AppBean info : appBeanList) {
                if (info.getLabel().equals("逍遥市场") && info.getPackageName().equals("com.microvirt.market")) {
                    object.put(Constant.Simulator.IS_SIMULATOR, true);
                    object.put(Constant.Simulator.SIMULATOR_NAME, "逍遥模拟器");
                    object.put(Constant.Simulator.SIMULATOR_INFO, info);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
