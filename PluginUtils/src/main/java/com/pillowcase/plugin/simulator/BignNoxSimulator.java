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
 * Description ： 夜神模拟器
 */
public class BignNoxSimulator extends SimpleSimulator {

    @Override
    public void initData() {
        SimulatorName = "夜神模拟器";
        AppLabelName = "游戏中心";
        PackageName = "com.bignox.app.store.hd";
    }

    @Override
    public boolean isSimulator(DeviceBean deviceBean, List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList, List<AppBean> installAppList) {
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put(IS_SIMULATOR, false);
            dataObject.put(SIMULATOR_NAME, SimulatorName);

            //处理器信息(Board):shamu , 渠道信息(Flavor):aosp_shamu-user
            if (deviceBean.getBoard().equals("shamu") && deviceBean.getFlavor().equals("aosp_shamu-user")) {
                dataObject.put(SIMULATOR_BOARD_INFO, deviceBean.getBoard());
                dataObject.put(SIMULATOR_FLAVOR_INFO, deviceBean.getFlavor());
                return LoggerInfo(true, dataObject);
            }

            for (AppBean bean : installAppList) {
                if (bean.checkSimulator(AppLabelName, PackageName)) {
                    dataObject.put(SIMULATOR_APP_INFO, bean);
                    return LoggerInfo(true, dataObject);
                }
                if (bean.checkSimulator(AppLabelName, "com.vphone.launcher")) {
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
