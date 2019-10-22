package com.pillowcase.normal.tools.emulator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;

import com.pillowcase.normal.tools.emulator.model.AppInfo;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author      : PillowCase
 * Created On ： 2019-08-02 15:24
 * Description ：
 */
public class EmulatorUtls {
    private static List<AppInfo> appList;
    private static List<String> labels;
    private static List<String> packageNameList;

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
     * 判断是否可以跳转到拨号界面
     */
    private static boolean canResolveIntent(Context context) {
        boolean canResolveIntent = false;
        try {
            String url = "tel:" + "123456";
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
     * 获取手机所有应用
     */
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
}
