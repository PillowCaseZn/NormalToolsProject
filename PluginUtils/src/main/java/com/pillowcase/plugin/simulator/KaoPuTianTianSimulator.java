package com.pillowcase.plugin.simulator;

import android.app.ActivityManager;

import com.pillowcase.models.AppBean;
import com.pillowcase.models.DeviceBean;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 15:26
 * Description ： 靠谱/天天模拟器
 */
public class KaoPuTianTianSimulator extends SimpleSimulator {

    @Override
    public void initData() {
        SimulatorName = "靠谱/天天模拟器";
    }

    @Override
    public boolean isSimulator(DeviceBean deviceBean, List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList, List<AppBean> installAppList) {
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put(IS_SIMULATOR, false);
            dataObject.put(SIMULATOR_NAME, SimulatorName);

            List<String> checkDataList = new ArrayList<>();
            checkDataList.add("com.tiantian.ime");
            checkDataList.add("com.kaopu.googleinstaller");

            for (AppBean bean : installAppList) {
                checkDataList.remove(bean.getPackageName());
            }

            if (checkDataList.size() == 0) {
                return LoggerInfo(true, dataObject);
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return LoggerInfo(false, dataObject);
    }
}
