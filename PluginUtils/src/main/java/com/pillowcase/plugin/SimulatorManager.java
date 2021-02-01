package com.pillowcase.plugin;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;

import com.pillowcase.plugin.interfaces.ISimulatorDetectionCallback;
import com.pillowcase.plugin.modules.AppBean;
import com.pillowcase.plugin.modules.DeviceBean;
import com.pillowcase.plugin.simulator.BignNoxSimulator;
import com.pillowcase.plugin.simulator.BlueStacksSimulator;
import com.pillowcase.plugin.simulator.FlySilkWormSimulator;
import com.pillowcase.plugin.simulator.KaoPuTianTianSimulator;
import com.pillowcase.plugin.simulator.MicrovirtSimulator;
import com.pillowcase.plugin.simulator.MuMuSimulator;
import com.pillowcase.plugin.simulator.SimpleSimulator;
import com.pillowcase.plugin.simulator.TencentSimulator;
import com.pillowcase.plugin.utils.AppUtils;
import com.pillowcase.plugin.utils.PluginLog;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 15:07
 * Description ： 模拟器检测
 */
public class SimulatorManager {
    @SuppressLint("StaticFieldLeak")
    private static SimulatorManager instance;
    private Activity mActivity;
    /**
     * 当前设备信息
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

    public SimulatorManager() {
        try {
            if (this.mDeviceBean == null) {
                this.mDeviceBean = new DeviceBean();
            }
            if (this.mRunningAppProcessInfoList == null) {
                this.mRunningAppProcessInfoList = new ArrayList<>();
            }
            if (this.mInstallAppList == null) {
                this.mInstallAppList = new ArrayList<>();
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    public static SimulatorManager getInstance() {
        if (instance == null) {
            synchronized (SimulatorManager.class) {
                if (instance == null) {
                    instance = new SimulatorManager();
                }
            }
        }
        return instance;
    }

    /**
     * 检测
     *
     * @param activity 上下文
     * @param callback 回调接口
     */
    public void detection(Activity activity, ISimulatorDetectionCallback callback) {
        try {
            this.mActivity = activity;

            // 首先通过设备信息判定是否是模拟器
            boolean isSimulator = deviceInfoCheck();
            if (isSimulator) {
                callback.onResult(true);
            } else {
                // 其次通过检测进程和安装的模拟器专属APP
                ActivityManager manager = (ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE);
                if (manager != null && manager.getRunningAppProcesses() != null) {
                    this.mRunningAppProcessInfoList.addAll(manager.getRunningAppProcesses());
                }

                this.mInstallAppList = AppUtils.getInstalledAppList(activity);

                callback.onResult(SimulatorCheck());
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
    }

    private boolean SimulatorCheck() {
        try {
            SimpleSimulator simulator = new FlySilkWormSimulator();
            if (simulator.isSimulator(this.mDeviceBean, this.mRunningAppProcessInfoList, this.mInstallAppList)) {
                PluginLog.log("this Device is Simulator , Simulator Name " + simulator.getSimulatorName());
                return true;
            }

            simulator = new TencentSimulator();
            if (simulator.isSimulator(this.mDeviceBean, this.mRunningAppProcessInfoList, this.mInstallAppList)) {
                PluginLog.log("this Device is Simulator , Simulator Name " + simulator.getSimulatorName());
                return true;
            }

            simulator = new BignNoxSimulator();
            if (simulator.isSimulator(this.mDeviceBean, this.mRunningAppProcessInfoList, this.mInstallAppList)) {
                PluginLog.log("this Device is Simulator , Simulator Name " + simulator.getSimulatorName());
                return true;
            }

            simulator = new BlueStacksSimulator();
            if (simulator.isSimulator(this.mDeviceBean, this.mRunningAppProcessInfoList, this.mInstallAppList)) {
                PluginLog.log("this Device is Simulator , Simulator Name " + simulator.getSimulatorName());
                return true;
            }

            simulator = new KaoPuTianTianSimulator();
            if (simulator.isSimulator(this.mDeviceBean, this.mRunningAppProcessInfoList, this.mInstallAppList)) {
                PluginLog.log("this Device is Simulator , Simulator Name " + simulator.getSimulatorName());
                return true;
            }

            simulator = new MicrovirtSimulator();
            if (simulator.isSimulator(this.mDeviceBean, this.mRunningAppProcessInfoList, this.mInstallAppList)) {
                PluginLog.log("this Device is Simulator , Simulator Name " + simulator.getSimulatorName());
                return true;
            }

            simulator = new MuMuSimulator();
            if (simulator.isSimulator(this.mDeviceBean, this.mRunningAppProcessInfoList, this.mInstallAppList)) {
                PluginLog.log("this Device is Simulator , Simulator Name " + simulator.getSimulatorName());
                return true;
            }

        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }

    /**
     * @return 通过设备信息判定是否是模拟器
     */
    @SuppressLint("PrivateApi")
    private boolean deviceInfoCheck() {
        try {
            this.mDeviceBean.setBaseBand(String.valueOf(Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke("null", "gsm.version.baseband")));
            this.mDeviceBean.setPlatform(String.valueOf(Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke("null", "ro.board.platform")));
            this.mDeviceBean.setFlavor(String.valueOf(Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke("null", "ro.build.flavor")));

            if (mDeviceBean.getBaseBand() == null || mDeviceBean.getBaseBand().isEmpty() || mDeviceBean.getBaseBand().equals("")) {
                PluginLog.log("Device BaseBand Info is Null , this device is Simulator");
                return true;
            }

            int suspected = 0;
            if (mDeviceBean.getBoard().isEmpty() || mDeviceBean.getPlatform().isEmpty() || !mDeviceBean.getBoard().equals(mDeviceBean.getPlatform())) {
                //判断 主板平台 和 处理器信息 是否相等，一般来说都是相等的，如果不相等，有模拟器的嫌疑
                PluginLog.log("Device Board Info is Null or Platform Info is Null , or the Board Info is not equals the Platform Info , this device maybe Simulator");
                suspected++;
            }
            if (mDeviceBean.getFlavor().isEmpty() || mDeviceBean.getFlavor().contains("vbox")) {
                //判断 渠道信息 VBox 模拟器 会带有‘Vbox’字段
                PluginLog.log("Device Flavor Info is Null or the Flavor Info contains 'vbox' , this device maybe is Simulator");
                suspected++;
            }
            if (mDeviceBean.getFingerPrint().contains("test-keys") || mDeviceBean.getFingerPrint().contains("vbox")) {
                PluginLog.log("Device FingerPrint Info contains 'vbox' or contains 'test-keys' , this device maybe is Simulator");
                suspected++;
            }
            if (mDeviceBean.getManufacturer().contains("Tencent")) {
                PluginLog.log("Device Manufacturer contains 'Tencent' , this device maybe is Simulator");
                suspected++;
            }
            if (!AppUtils.canResolveIntent(this.mActivity)) {
                //夜神模拟器可以跳转到拨号界面
                PluginLog.log("Device can't open the Dial-Up Interface , this device maybe is Simulator");
                suspected++;
            }
            if (suspected > 3) {
                PluginLog.log("Device Suspected Number more than 3 , this device mostly is Simulator");
                return true;
            }

        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }
}
