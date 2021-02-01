package com.pillowcase.plugin.simulator;

import android.app.ActivityManager;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-06-22 13:53
 * Description ： 腾讯手游模拟器
 */
public class TencentSimulator extends SimpleSimulator {
    @Override
    public void initData() {
        SimulatorName = "腾讯手游模拟器";
        RunningProcess = "com.tencent.tinput";
    }

    @Override
    public boolean isSimulator(DeviceBean deviceBean, List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList, List<AppBean> installAppList) {
        JSONObject dataObject = new JSONObject();
        try {
            dataObject.put(IS_SIMULATOR, false);
            dataObject.put(SIMULATOR_NAME, SimulatorName);

            //设备品牌(Brand):tencent , 唯一标识(FingerPrint):tencent/vbox .. test-keys
            if (deviceBean.getBrand().equals("tencent") || (deviceBean.getFingerPrint().equals("tencent") || deviceBean.getFingerPrint().equals("vbox") || deviceBean.getFingerPrint().equals("test-keys"))) {
                dataObject.put(SIMULATOR_BRAND_INFO, deviceBean.getBrand());
                dataObject.put(SIMULATOR_FINGERPRINT_INFO, deviceBean.getFingerPrint());
                return LoggerInfo(true, dataObject);
            }
            // 进程判断
            for (ActivityManager.RunningAppProcessInfo progress : runningAppProcessInfoList) {
                if (progress.processName.equals(RunningProcess)) {
                    dataObject.put(SIMULATOR_RUNNING_PROCESS, RunningProcess);
                    return LoggerInfo(true, dataObject);
                }
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return LoggerInfo(false, dataObject);
    }
}
