package com.pillowcase.plugin.simulator;

import android.app.ActivityManager;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.Constant;
import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 蓝叠模拟器
 */
public class BlueStacksSimulator extends SimpleSimulator {

    @Override
    public void initData() {
        SimulatorName = "蓝叠模拟器";
    }

    @Override
    public boolean isSimulator(DeviceBean deviceBean, List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList, List<AppBean> installAppList) {
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put(IS_SIMULATOR, false);
            dataObject.put(SIMULATOR_NAME, SimulatorName);

            int suspected = 0;
            for (AppBean bean : installAppList) {
                String name = bean.getPackageName();
                if (name.equals("com.bluestacks.appfinder")
                        || name.equals("com.bluestacks.appmark")
                        || name.equals("com.bluestacks.setting")
                        || name.equals("com.bluestacks.searchapp")
                        || name.equals("com.bluestacks.setup")) {
                    suspected++;
                }
            }

            if (suspected > 2) {
                return LoggerInfo(true , dataObject);
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return LoggerInfo(false, dataObject);
    }
}
