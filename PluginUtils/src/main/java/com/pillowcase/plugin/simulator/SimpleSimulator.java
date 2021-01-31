package com.pillowcase.plugin.simulator;

import android.app.ActivityManager;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONObject;

import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 01:27
 * Description ： 模拟器
 */
public abstract class SimpleSimulator {
    protected static final String IS_SIMULATOR = "isSimulator";
    protected static final String SIMULATOR_NAME = "SimulatorName";
    protected static final String SIMULATOR_APP_INFO = "SimulatorAppInfo";
    protected static final String SIMULATOR_PLATFORM_INFO = "主板平台(Platform)";
    protected static final String SIMULATOR_FLAVOR_INFO = "渠道信息(Flavor)";
    protected static final String SIMULATOR_RUNNING_PROCESS = "运行进程";

    /**
     * 是否输出检测Json信息
     */
    private static final boolean isLoggerJsonInfo = false;

    protected String SimulatorName, AppLabelName, PackageName, RunningProcess;

    public SimpleSimulator() {
        initData();
    }

    protected boolean LoggerInfo(boolean result, JSONObject dataObject) {
        try {
            if (isLoggerJsonInfo) {
                PluginLog.log("Simulator Check Info " + dataObject);
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return result;
    }

    public String getSimulatorName() {
        return SimulatorName;
    }

    public abstract void initData();

    /**
     * @param deviceBean                设备信息
     * @param runningAppProcessInfoList 当前设备运行的进程
     * @param installAppList            设备已安装的App
     * @return 是否模拟器
     */
    public abstract boolean isSimulator(DeviceBean deviceBean, List<ActivityManager.RunningAppProcessInfo> runningAppProcessInfoList, List<AppBean> installAppList);
}
