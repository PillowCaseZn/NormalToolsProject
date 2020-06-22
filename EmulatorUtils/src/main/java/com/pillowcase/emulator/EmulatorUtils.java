package com.pillowcase.emulator;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import com.pillowcase.emulator.interfaces.IEmulatorCheckListener;
import com.pillowcase.emulator.model.AppInfo;
import com.pillowcase.emulator.model.Device;
import com.pillowcase.emulator.model.emulator.BignNox;
import com.pillowcase.emulator.model.emulator.BlueStacks;
import com.pillowcase.emulator.model.emulator.FlySilkWorm;
import com.pillowcase.emulator.model.emulator.KaoPuTianTian;
import com.pillowcase.emulator.model.emulator.Microvirt;
import com.pillowcase.emulator.model.emulator.MuMu;
import com.pillowcase.emulator.model.emulator.Tencent;
import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.logger.impl.ILoggerOperation;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author      : PillowCase
 * Created On ： 2019-08-02 15:24
 * Update  On ： 2020-06-20 13:11
 * Description ： 判断当前设备是否为模拟器
 */
public class EmulatorUtils implements ILoggerOperation {
    private LoggerUtils mLoggerUtils;
    private Context mContext;
    /**
     * 设备信息
     */
    private Device mDeviceInfo;
    /**
     * 返回的相关信息
     */
    private JSONObject mInfoObject;
    private IEmulatorCheckListener mListener;

    @Deprecated
    private static List<AppInfo> appList;
    @Deprecated
    private static List<String> labels;
    @Deprecated
    private static List<String> packageNameList;

    public EmulatorUtils() {
    }

    public EmulatorUtils(Context context, IEmulatorCheckListener listener) {
        try {
            log("EmulatorUtils", "");
            if (mLoggerUtils == null) {
                mLoggerUtils = new LoggerUtils(false, getClass().getSimpleName());
            }
            this.mListener = listener;
            this.mContext = context;
            check();
        } catch (Exception e) {
            error(e, "EmulatorUtils");
        }
    }

    /**
     * 检测模拟器信息
     */
    @SuppressLint("PrivateApi")
    public String test(Context context) {
        String deviceInfo = null;
        try {
            log("test", "");
            deviceInfo =
                    "设备信息:" + getDeviceInfo().toString() + "\n" +
                            "拨号界面:" + canResolveIntent(context) + "\n";

            //获取进程
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            if (manager != null && manager.getRunningAppProcesses() != null) {
                String processInfo = "进程信息:{" + "\n";
                for (ActivityManager.RunningAppProcessInfo info : manager.getRunningAppProcesses()) {
//                    log("test", "info : " + info.processName);
                    processInfo += "\u3000--> " + info.processName + "\n";
                }
                processInfo += "}\n";
                deviceInfo += processInfo;
            }

            //获取已安装的APP
            PackageManager packageManager = context.getPackageManager(); // 获得PackageManager对象
            if (packageManager != null) {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
                Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(packageManager));

                List<ResolveInfo> containsList = new ArrayList<>();
                for (ResolveInfo reInfo : resolveInfos) {
                    String label = (String) reInfo.loadLabel(packageManager); // 获得应用程序的Label
                    String packageName = reInfo.activityInfo.packageName; // 获得应用程序的包名

                    if (label.contains("应用商店") || label.contains("应用中心")
                            || label.contains("游戏中心") || label.contains("市场")) {
                        if (!containsList.contains(reInfo)) {
                            containsList.add(reInfo);
                        }
                    }
                    if (label.contains("逍遥") || label.contains("蓝叠")
                            || label.contains("夜神") || label.contains("MuMu")
                            || label.contains("靠谱") || label.contains("天天")) {
                        if (!containsList.contains(reInfo)) {
                            containsList.add(reInfo);
                        }
                    }
                    if (packageName.contains("kaopu") || packageName.contains("bignox")
                            || packageName.contains("tiantian") || packageName.contains("flysilkworm")
                            || packageName.contains("tencent") || packageName.contains("mumu")) {
                        if (!containsList.contains(reInfo)) {
                            containsList.add(reInfo);
                        }
                    }
                }
                if (containsList.size() > 0) {
                    String appInfo = "App信息:{" + "\n";
                    for (ResolveInfo info : containsList) {
                        String label = (String) info.loadLabel(packageManager); // 获得应用程序的Label
                        String packageName = info.activityInfo.packageName; // 获得应用程序的包名
                        appInfo += "\u3000--> " + label + "\u2000" + packageName + "\n";
                    }
                    appInfo += "}\n";
                    deviceInfo += appInfo;
                }
            }

            log("test", deviceInfo);
        } catch (Exception e) {
            error(e, "test");
        }
        return deviceInfo;
    }

    @SuppressLint("PrivateApi")
    private void check() {
        try {
            boolean isEmulator = false;
            mInfoObject = new JSONObject();
            mDeviceInfo = getDeviceInfo();

            //判断 基带信息 是否为空 ，为空则是 模拟器
            mInfoObject.put("基带信息(BaseBand)", mDeviceInfo.getBaseBand());
            if (mDeviceInfo.getBaseBand() == null || mDeviceInfo.getBaseBand().isEmpty() || mDeviceInfo.getBaseBand().equals("")) {
                isEmulator = true;
            }

            //根据设备信息和是否可以跳转到拨号界面来判断是否是模拟器
            boolean deviceCheck = DeviceCheck();
            boolean canResolveIntent = canResolveIntent(this.mContext);

            if (deviceCheck && !canResolveIntent) {
                isEmulator = true;
                mInfoObject.put("拨号界面", canResolveIntent);
            }

            //获取进程
            List<ActivityManager.RunningAppProcessInfo> processList = new ArrayList<>();
            ActivityManager manager = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
            if (manager != null && manager.getRunningAppProcesses() != null) {
                processList.addAll(manager.getRunningAppProcesses());
            }
            //获取已安装的APP
            List<AppInfo> appList = new ArrayList<>();
            PackageManager packageManager = mContext.getPackageManager(); // 获得PackageManager对象
            if (packageManager != null) {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfos = packageManager.queryIntentActivities(intent, 0);
                Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(packageManager));

                for (ResolveInfo info : resolveInfos) {
                    String label = (String) info.loadLabel(packageManager); // 获得应用程序的Label
                    String packageName = info.activityInfo.packageName; // 获得应用程序的包名

                    AppInfo app = new AppInfo();
                    app.setLabel(label);
                    app.setPackageName(packageName);
                    appList.add(app);
                }
            }
            //根据统计的模拟器信息判断是否为模拟器
            JSONObject object = FlySilkWorm.isEmulator(mDeviceInfo, processList, appList);
            log("check", "Emulator Json : " + object);
            if (isEmulator(object)) {
                isEmulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = KaoPuTianTian.isEmulator(appList);
            log("check", "Emulator Json : " + object);
            if (isEmulator(object)) {
                isEmulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = BignNox.isEmulator(mDeviceInfo, appList);
            log("check", "Emulator Json : " + object);
            if (isEmulator(object)) {
                isEmulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = Tencent.isEmulator(mDeviceInfo, processList);
            log("check", "Emulator Json : " + object);
            if (isEmulator(object)) {
                isEmulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = MuMu.isEmulator(mDeviceInfo, appList);
            log("check", "Emulator Json : " + object);
            if (isEmulator(object)) {
                isEmulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = Microvirt.isEmulator(appList);
            log("check", "Emulator Json : " + object);
            if (isEmulator(object)) {
                isEmulator = true;
                mInfoObject.put("模拟器信息", object);
            }

            object = BlueStacks.isEmulator(appList);
            log("check", "Emulator Json : " + object);
            if (isEmulator(object)) {
                isEmulator = true;
                mInfoObject.put("模拟器信息", object);
            }


            mListener.result(isEmulator, mInfoObject.toString());
        } catch (Exception e) {
            error(e, "check");
        }
    }

    private boolean isEmulator(JSONObject object) {
        try {
            log("isEmulator", "");
            if (object.has("isEmulator")) {
                return object.getBoolean("isEmulator");
            }
        } catch (Exception e) {
            error(e, "isEmulator");
        }
        return false;
    }

    /**
     * 设备信息检测
     */
    private boolean DeviceCheck() {
        try {
            log("DeviceCheck", "");
            int suspected = 0;

            if (mDeviceInfo.getBoard().isEmpty() || mDeviceInfo.getPlatform().isEmpty() || !mDeviceInfo.getBoard().equals(mDeviceInfo.getPlatform())) {
                //判断 主板平台 和 处理器信息 是否相等，一般来说都是相等的，如果不相等，有模拟器的嫌疑
                mInfoObject.put("处理器信息(Board)", mDeviceInfo.getBoard());
                mInfoObject.put("主板平台(Platform)", mDeviceInfo.getPlatform());
                suspected++;
            }
            if (mDeviceInfo.getFlavor().isEmpty() || mDeviceInfo.getFlavor().contains("vbox")) {
                //判断 渠道信息 VBox 模拟器 会带有‘Vbox’字段
                mInfoObject.put("渠道信息(Flavor)", mDeviceInfo.getFlavor());
                suspected++;
            }
            if (mDeviceInfo.getFingerPrint().contains("test-keys") || mDeviceInfo.getFingerPrint().contains("vbox")) {
                //腾讯手游模拟器
                mInfoObject.put("唯一标识(FingerPrint)", mDeviceInfo.getFingerPrint());
                suspected++;
            }
            if (mDeviceInfo.getManufacturer().contains("Tencent")) {
                //腾讯手游模拟器
                mInfoObject.put("设备制造商(Manufacturer)", mDeviceInfo.getManufacturer());
                suspected++;
            }

            log("DeviceCheck", "suspected : " + suspected);
            if (suspected >= 2) {
                return true;
            }
        } catch (Exception e) {
            error(e, "DeviceCheck");
        }
        return false;
    }

    /**
     * 获取设备信息
     */
    @SuppressLint("PrivateApi")
    private Device getDeviceInfo() {
        Device device = new Device();
        try {
            device.setBaseBand(String.valueOf(Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke("null", "gsm.version.baseband")));
            device.setPlatform(String.valueOf(Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke("null", "ro.board.platform")));
            device.setFlavor(String.valueOf(Class.forName("android.os.SystemProperties").getMethod("get", String.class).invoke("null", "ro.build.flavor")));
            log("getDeviceInfo", device);
        } catch (Exception e) {
            error(e, "getDeviceInfo");
        }
        return device;
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

    @Deprecated
    public static String isEmulator(Context context) {
        String data = "";
        try {
            //获取手机所有应用
            appList = new ArrayList<>();
            labels = new ArrayList<>();
            packageNameList = new ArrayList<>();
            getAllApp(context);

            final JSONObject object = new JSONObject();
            object.put("isEmulator", false);

            String model = Build.MODEL;
            String fingerprint = Build.FINGERPRINT;
            String manufacturer = Build.MANUFACTURER;
            object.put("Model", model);
            object.put("Fingerprint", fingerprint);
            object.put("Manufacturer", manufacturer);

            if (fingerprint.contains("test-keys") || fingerprint.contains("vbox")) {
                object.put("isEmulator", true);
                object.put("Emulator", "模拟器");
                //TapTap模拟器

                //腾讯手游模拟器
                if (manufacturer.equals("Tencent")) {
                    object.put("Emulator", "腾讯手游模拟器");
                }
            } else {
                boolean canResolveIntent = canResolveIntent(context);
                object.put("canResolveIntent", canResolveIntent);

                if (canResolveIntent) {
                    //蓝叠模拟器
                    boolean blueStacksEmulator = isBlueStacksEmulator(context);
                    if (blueStacksEmulator) {
                        object.put("isEmulator", true);
                        object.put("Emulator", "蓝叠模拟器");
                    }

                    //夜神模拟器
                    boolean noxEmulator = isNoxEmulator(context);
                    if (noxEmulator) {
                        object.put("isEmulator", true);
                        object.put("Emulator", "夜神模拟器");
                    }
                } else {
                    //逍遥模拟器
                    boolean isXiaoYaoEmulator = isXiaoYaoEmulator(context);
                    if (isXiaoYaoEmulator) {
                        object.put("isEmulator", true);
                        object.put("Emulator", "逍遥模拟器");
                    }

                    //MuMu模拟器
                    boolean isMuMuEmulator = isMuMuEmulator(context);
                    if (isMuMuEmulator || model.equals("MuMu")) {
                        object.put("isEmulator", true);
                        object.put("Emulator", "MuMu模拟器");
                    }

                    //雷电模拟器
                    if (packageNameList.contains("com.android.flysilkworm") && labels.contains("雷电游戏中心")) {
                        object.put("isEmulator", true);
                        object.put("Emulator", "雷电模拟器");
                    }
                }
            }
            data = object.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    /**
     * 判断是否夜神模拟器
     */
    private static boolean isNoxEmulator(Context context) {
        boolean isNoxEmulator = false;
        try {
            isNoxEmulator = packageNameList.contains("com.bignox.app.store.hd") || packageNameList.contains("com.vphone.launcher");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isNoxEmulator;
    }

    /**
     * 判断是否蓝叠模拟器
     */
    private static boolean isBlueStacksEmulator(Context context) {
        boolean isBlueStacksEmulator = false;
        try {
            isBlueStacksEmulator = packageNameList.contains("com.bluestacks.appfinder")
                    || packageNameList.contains("com.bluestacks.appmark")
                    || packageNameList.contains("com.bluestacks.searchapp")
                    || packageNameList.contains("com.bluestacks.setting")
                    || packageNameList.contains("com.bluestacks.setup");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isBlueStacksEmulator;
    }

    /**
     * 判断是否MuMu模拟器
     */
    private static boolean isMuMuEmulator(Context context) {
        boolean isMuMuEmulator = false;
        try {
            boolean b = packageNameList.contains("com.mumu.store") || packageNameList.contains("io.kkzs");
            boolean b1 = labels.contains("应用中心") || labels.contains("KK谷歌助手");

            isMuMuEmulator = b && b1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isMuMuEmulator;
    }

    /**
     * 判断是否逍遥模拟器
     */
    private static boolean isXiaoYaoEmulator(Context context) {
        boolean isXiaoYaoEmulator = false;
        try {
            boolean b = packageNameList.contains("com.microvirt.market") || packageNameList.contains("com.microvirt.guide");
            boolean b1 = labels.contains("逍遥市场") || labels.contains("逍遥向导");

            isXiaoYaoEmulator = b && b1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return isXiaoYaoEmulator;
    }


    /**
     * 获取手机所有应用
     */
    @Deprecated
    private static void getAllApp(Context context) {
        try {
            PackageManager pm = context.getPackageManager(); // 获得PackageManager对象
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);
            List<ResolveInfo> resolveInfos = pm.queryIntentActivities(mainIntent, 0);
            Collections.sort(resolveInfos, new ResolveInfo.DisplayNameComparator(pm));
            for (ResolveInfo reInfo : resolveInfos) {
                String pkgName = reInfo.activityInfo.packageName; // 获得应用程序的包名
                String appLabel = (String) reInfo.loadLabel(pm); // 获得应用程序的Label

                AppInfo info = new AppInfo(pkgName, appLabel);
                appList.add(info);

                labels.add(appLabel);
                packageNameList.add(pkgName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void log(String method, Object object) {
        if (mLoggerUtils != null) {
            mLoggerUtils.log(method, object);
        }
    }

    @Override
    public void warn(String method, String message) {
        if (mLoggerUtils != null) {
            mLoggerUtils.warn(method, message);
        }
    }

    @Override
    public void error(Throwable throwable, String method) {
        if (mLoggerUtils != null) {
            mLoggerUtils.error(throwable, method);
        }
    }
}
