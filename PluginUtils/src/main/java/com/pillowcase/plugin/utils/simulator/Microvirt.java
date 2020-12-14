package com.pillowcase.plugin.utils.simulator;


import com.pillowcase.plugin.modules.App;
import com.pillowcase.plugin.modules.SimulatorConstant;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 逍遥模拟器
 */
public class Microvirt {
    public static JSONObject isSimulator(List<App> appList) {
        JSONObject object = new JSONObject();
        try {
            for (App info : appList) {
                if (info.getLabel().equals("逍遥市场") && info.getPackageName().equals("com.microvirt.market")) {
                    object.put(SimulatorConstant.IS_SIMULATOR, true);
                    object.put(SimulatorConstant.SIMULATOR_NAME, "逍遥模拟器");
                    object.put(SimulatorConstant.SIMULATOR_INFO, info);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
