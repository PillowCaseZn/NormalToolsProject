package com.pillowcase.plugin.simulator;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.utils.AppUtils;
import com.pillowcase.plugin.utils.PluginLog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-01 01:27
 * Description ： 模拟器
 */
public abstract class SimpleSimulator {
    /**
     * 设备信息
     */
    private DeviceBean mDeviceBean;
    /**
     * 获取当前设备运行的进程
     */
    private List<ActivityManager.RunningAppProcessInfo> mRunningAppProcessInfoList;
    /**
     * 设备已安装的App
     */
    private List<AppBean> mInstallAppList;

    public void init(Activity activity) {
        try {
            if (this.mDeviceBean == null) {
                this.mDeviceBean = new DeviceBean();
            }

            if (this.mRunningAppProcessInfoList == null) {
                this.mRunningAppProcessInfoList = new ArrayList<>();
                ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
                if (manager != null && manager.getRunningAppProcesses() != null) {
                    this.mRunningAppProcessInfoList.addAll(manager.getRunningAppProcesses());
                }
            }

            if (this.mInstallAppList == null) {
                this.mInstallAppList = AppUtils.getInstalledAppList(activity);
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    abstract JSONObject isSimulator();
}
