package com.pillowcase.plugin.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.pillowcase.plugin.modules.App;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 14:59
 * Description ： 获取当前设备已安装APP的信息
 */
public class InstalledAppUtils {
    public static List<App> getInstalledAppList(Context context) {
        List<App> appList = new ArrayList<>();
        try {
            PackageManager manager = context.getPackageManager();
            if (manager != null) {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfoList = manager.queryIntentActivities(intent, 0);
                Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(manager));

                for (ResolveInfo info : resolveInfoList) {
                    App app = new App();
                    app.setAppName(String.valueOf(info.loadLabel(manager)));
                    app.setPackageName(info.activityInfo.packageName);
                    app.setApplicationName(info.activityInfo.applicationInfo.className);
                    if (((ApplicationInfo.FLAG_SYSTEM & info.activityInfo.applicationInfo.flags) == 0)
                            && ((ApplicationInfo.FLAG_UPDATED_SYSTEM_APP & info.activityInfo.applicationInfo.flags) == 0)
                            && ((ApplicationInfo.FLAG_STOPPED & info.activityInfo.applicationInfo.flags) == 0)) {
                        app.setRunning(true);
                    }

                    if (!appList.contains(app)) {
                        appList.add(app);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return appList;
    }
}
