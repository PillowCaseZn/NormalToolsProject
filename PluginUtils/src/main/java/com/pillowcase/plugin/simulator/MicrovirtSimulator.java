package com.pillowcase.plugin.simulator;


import android.app.ActivityManager;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.Constant;
import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 逍遥模拟器
 */
public class MicrovirtSimulator extends SimpleSimulator{

    @Override
    public void initData() {
        SimulatorName = "逍遥模拟器";
        AppLabelName = "逍遥市场";
        PackageName = "com.microvirt.market";
    }

    @Override
    public boolean isSimulator(DeviceBean deviceBean, List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList, List<AppBean> installAppList) {
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put(IS_SIMULATOR, false);
            dataObject.put(SIMULATOR_NAME, SimulatorName);

            for (AppBean bean : installAppList) {
                if (bean.checkSimulator(AppLabelName, PackageName)) {
                    dataObject.put(SIMULATOR_APP_INFO, bean);
                    return LoggerInfo(true, dataObject);
                }
            }

        } catch (Exception e) {
            PluginLog.error(e);
        }
        return LoggerInfo(false, dataObject);
    }
}
