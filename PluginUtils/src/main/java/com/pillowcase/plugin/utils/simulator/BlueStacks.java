package com.pillowcase.plugin.utils.simulator;

import com.pillowcase.plugin.modules.App;
import com.pillowcase.plugin.modules.SimulatorConstant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 蓝叠模拟器
 */
public class BlueStacks {
    public static JSONObject isSimulator(List<App> appList) {
        JSONObject object = new JSONObject();
        try {
            List<String> nameList = new ArrayList<>();

            for (App info : appList) {
                nameList.add(info.getPackageName());
            }
            int suspected = 0;
            if (nameList.contains("com.bluestacks.appfinder")) {
                suspected++;
            }
            if (nameList.contains("com.bluestacks.appmark")) {
                suspected++;
            }
            if (nameList.contains("com.bluestacks.setting")) {
                suspected++;
            }
            if (nameList.contains("com.bluestacks.searchapp")) {
                suspected++;
            }
            if (nameList.contains("com.bluestacks.setup")) {
                suspected++;
            }
            if (suspected >= 2) {
                object.put(SimulatorConstant.IS_SIMULATOR, true);
                object.put(SimulatorConstant.SIMULATOR_NAME, "蓝叠模拟器");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
