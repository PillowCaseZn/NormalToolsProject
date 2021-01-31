package com.pillowcase.plugin.simulator;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.Constant;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 15:26
 * Description ：
 */
public class KaoPuTianTianSimulator {
    public static JSONObject isSimulator(List<AppBean> appBeanList) {
        JSONObject object = new JSONObject();
        try {
            List<String> nameList = new ArrayList<>();

            for (AppBean info : appBeanList) {
                nameList.add(info.getPackageName());
            }
            if (nameList.contains("com.tiantian.ime") && nameList.contains("com.kaopu.googleinstaller")) {
                object.put(Constant.Simulator.IS_SIMULATOR, true);
                object.put(Constant.Simulator.SIMULATOR_NAME, "靠谱/天天模拟器");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return object;
    }
}
