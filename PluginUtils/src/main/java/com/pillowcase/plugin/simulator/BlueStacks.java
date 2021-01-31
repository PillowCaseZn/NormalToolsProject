package com.pillowcase.plugin.simulator;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.Constant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 蓝叠模拟器
 */
public class BlueStacks {
    public static JSONObject isSimulator(List<AppBean> appBeanList) {
        JSONObject object = new JSONObject();
        try {
            List<String> nameList = new ArrayList<>();

            for (AppBean info : appBeanList) {
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
                object.put(Constant.Simulator.IS_SIMULATOR, true);
                object.put(Constant.Simulator.SIMULATOR_NAME, "蓝叠模拟器");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
