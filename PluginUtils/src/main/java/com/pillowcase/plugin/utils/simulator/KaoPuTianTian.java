package com.pillowcase.plugin.utils.simulator;

import com.pillowcase.plugin.modules.App;
import com.pillowcase.plugin.modules.SimulatorConstant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 15:26
 * Description ：
 */
public class KaoPuTianTian {
    public static JSONObject isSimulator(List<App> appList) {
        JSONObject object = new JSONObject();
        try {
            List<String> nameList = new ArrayList<>();

            for (App info : appList) {
                nameList.add(info.getPackageName());
            }
            if (nameList.contains("com.tiantian.ime") && nameList.contains("com.kaopu.googleinstaller")) {
                object.put(SimulatorConstant.IS_SIMULATOR, true);
                object.put(SimulatorConstant.SIMULATOR_NAME, "靠谱/天天模拟器");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
