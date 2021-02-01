package com.pillowcase.plugin.simulator;

import android.app.ActivityManager;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 01:54
 * Description ： 雷电模拟器
 */
public class FlySilkWormSimulator extends SimpleSimulator {

    @Override
    public void initData() {
        SimulatorName = "雷电模拟器";
        AppLabelName = "雷电游戏中心";
        PackageName = "com.android.flysilkworm";
        RunningProcess = "com.android.flysilkworm";
    }

    @Override
    public boolean isSimulator(DeviceBean deviceBean, List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList, List<AppBean> installAppList) {
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put(IS_SIMULATOR, false);
            dataObject.put(SIMULATOR_NAME, SimulatorName);

            //主板平台(Platform):aosp-user , 渠道信息(Flavor):aosp-user
            if (deviceBean.getPlatform().equals("aosp-user") && deviceBean.getFlavor().equals("aosp-user")) {
                dataObject.put(SIMULATOR_PLATFORM_INFO, deviceBean.getPlatform());
                dataObject.put(SIMULATOR_FLAVOR_INFO, deviceBean.getFlavor());
                return LoggerInfo(true, dataObject);
            }
            // 进程判断
            for (ActivityManager.RunningAppProcessInfo progress : runningAppProcessInfoList) {
                if (progress.processName.equals(RunningProcess)) {
                    dataObject.put(SIMULATOR_RUNNING_PROCESS, RunningProcess);
                    return LoggerInfo(true, dataObject);
                }
            }

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
