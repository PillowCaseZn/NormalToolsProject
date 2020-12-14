package com.pillowcase.plugin;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.pillowcase.plugin.interfaces.ISimulatorDetectionCallback;
import com.pillowcase.plugin.modules.App;
import com.pillowcase.plugin.modules.Device;
import com.pillowcase.plugin.modules.SimulatorConstant;
import com.pillowcase.plugin.utils.simulator.BignNox;
import com.pillowcase.plugin.utils.simulator.BlueStacks;
import com.pillowcase.plugin.utils.simulator.FlySilkWorm;
import com.pillowcase.plugin.utils.simulator.KaoPuTianTian;
import com.pillowcase.plugin.utils.simulator.Microvirt;
import com.pillowcase.plugin.utils.simulator.MuMu;
import com.pillowcase.plugin.utils.simulator.Tencent;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 15:07
 * Description ： 模拟器检测
 */
public class SimulatorDetection {
    private static SimulatorDetection instance;
    private Context mContext;
    private ISimulatorDetectionCallback mCallback;
    /**
     * 当前设备信息
     */
    private Device mDevice;
    /**
     * 返回的相关信息
     */
    private JSONObject mInfoObject;

    public SimulatorDetection() {
    }

    public static SimulatorDetection getInstance() {
        synchronized (SimulatorDetection.class) {
            if (instance == null) {
                instance = new SimulatorDetection();
            }
        }
        return instance;
    }

    public void detection(Context context, ISimulatorDetectionCallback callback) {
        try {
            this.mContext = context;
            this.mCallback = callback;

            loadDeviceInfo();
            detection();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void detection() {
        try {
            mInfoObject = new JSONObject();
            /**
             * 是否模拟器
             */
            boolean isSimulator = false;

            //判断 基带信息 是否为空 ，为空则是 模拟器
            mInfoObject.put("基带信息(BaseBand)", mDevice.getBaseBand());
            if (mDevice.getBaseBand() == null || mDevice.getBaseBand().isEmpty() || mDevice.getBaseBand().equals("")) {
                isSimulator = true;
            }

            //根据设备信息和是否可以跳转到拨号界面来判断是否是模拟器
            boolean deviceCheck = DeviceCheck();
            boolean canResolveIntent = canResolveIntent(this.mContext);

            if (deviceCheck && !canResolveIntent) {
                isSimulator = true;
                mInfoObject.put("拨号界面", canResolveIntent);
            }

            //获取进程
            List<ActivityManager.RunningAppProcessInfo> processList = new ArrayList<>();
            ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            if (manager != null && manager.getRunningAppProcesses() != null) {
                processList.addAll(manager.getRunningAppProcesses());
            }
            //获取已安装的APP
            List<App> appList = new ArrayList<>();
            PackageManager packageManager = mContext.getPackageManager(); // 获得PackageManager对象
            if (packageManager != null) {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
                Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(packageManager));

                for (ResolveInfo info : resolveInfos) {
                    String label = (String) info.loadLabel(packageManager); // 获得应用程序的Label
                    String packageName = info.activityInfo.packageName; // 获得应用程序的包名

                    App app = new App();
                    app.setLabel(label);
                    app.setPackageName(packageName);
                    appList.add(app);
                }
            }
            //根据统计的模拟器信息判断是否为模拟器
            JSONObject object = FlySilkWorm.isSimulator(mDevice, processList, appList);
            if (isSimulatorCheck(object)) {
                isSimulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = KaoPuTianTian.isSimulator(appList);
            if (isSimulatorCheck(object)) {
                isSimulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = BignNox.isSimulator(mDevice, appList);
            if (isSimulatorCheck(object)) {
                isSimulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = Tencent.isSimulator(mDevice, processList);
            if (isSimulatorCheck(object)) {
                isSimulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = MuMu.isSimulator(mDevice, appList);
            if (isSimulatorCheck(object)) {
                isSimulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = Microvirt.isSimulator(appList);
            if (isSimulatorCheck(object)) {
                isSimulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = BlueStacks.isSimulator(appList);
            if (isSimulatorCheck(object)) {
                isSimulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            if (mCallback != null) {
                mCallback.onResult(isSimulator, mInfoObject.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据返回的Json字段，判断是否模拟器
     */
    private boolean isSimulatorCheck(JSONObject object) {
        try {
            if (object.has(SimulatorConstant.IS_SIMULATOR)) {
                return object.getBoolean(SimulatorConstant.IS_SIMULATOR);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断是否可以跳转到拨号界面
     * 夜神模拟器可以跳转到拨号界面
     */
    private static boolean canResolveIntent(Context context) {
        boolean canResolveIntent = false;
        try {
            String url = "tel:" + "10000";
            Intent intent = new Intent();
            intent.setData(Uri.parse(url));
            intent.setAction(Intent.ACTION_DIAL);
            // 是否可以处理跳转到拨号的 Intent
            canResolveIntent = intent.resolveActivity(context.getPackageManager()) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return canResolveIntent;
    }

    /**
     * 设备信息检测
     */
    private boolean DeviceCheck() {
        try {
            int suspected = 0;

            if (mDevice.getBoard().isEmpty() || mDevice.getPlatform().isEmpty() || !mDevice.getBoard().equals(mDevice.getPlatform())) {
                //判断 主板平台 和 处理器信息 是否相等，一般来说都是相等的，如果不相等，有模拟器的嫌疑
                mInfoObject.put("处理器信息(Board)", mDevice.getBoard());
                mInfoObject.put("主板平台(Platform)", mDevice.getPlatform());
                suspected++;
            }
            if (mDevice.getFlavor().isEmpty() || mDevice.getFlavor().contains("vbox")) {
                //判断 渠道信息 VBox 模拟器 会带有‘Vbox’字段
                mInfoObject.put("渠道信息(Flavor)", mDevice.getFlavor());
                suspected++;
            }
            if (mDevice.getFingerPrint().contains("test-keys") || mDevice.getFingerPrint().contains("vbox")) {
                //腾讯手游模拟器
                mInfoObject.put("唯一标识(FingerPrint)", mDevice.getFingerPrint());
                suspected++;
            }
            if (mDevice.getManufacturer().contains("Tencent")) {
                //腾讯手游模拟器
                mInfoObject.put("设备制造商(Manufacturer)", mDevice.getManufacturer());
                suspected++;
            }

            if (suspected >= 2) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @SuppressLint("PrivateApi")
    private void loadDeviceInfo() {
        try {
            mDevice = new Device();
            mDevice.setBaseBand(String.valueOf(Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke("null", "gsm.version.baseband")));
            mDevice.setPlatform(String.valueOf(Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke("null", "ro.board.platform")));
            mDevice.setFlavor(String.valueOf(Class.forName("android.os.SystemProperties")
                    .getMethod("get", String.class)
                    .invoke("null", "ro.build.flavor")));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
