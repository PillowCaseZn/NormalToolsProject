package com.pillowcase.plugin.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.pillowcase.plugin.modules.AppBean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-12-14 14:59
 * Description ： App 操作
 */
public class AppUtils {
    /**
     * @param context 上下文
     * @return 获取当前设备已安装APP的信息
     */
    public static List<AppBean> getInstalledAppList(Context context) {
        List<AppBean> appBeanList = new ArrayList<>();
        try {
            PackageManager manager = context.getPackageManager();
            if (manager != null) {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfoList = manager.queryIntentActivities(intent, 0);
                Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(manager));

                for (ResolveInfo info : resolveInfoList) {
                    AppBean bean = new AppBean();
                    bean.setAppName(String.valueOf(info.loadLabel(manager)));
                    bean.setPackageName(info.activityInfo.packageName);
                    bean.setApplicationName(info.activityInfo.applicationInfo.className);
                    if (((ApplicationInfo.FLAG_SYSTEM & info.activityInfo.applicationInfo.flags) == 0)
                            && ((ApplicationInfo.FLAG_UPDATED_SYSTEM_APP & info.activityInfo.applicationInfo.flags) == 0)
                            && ((ApplicationInfo.FLAG_STOPPED & info.activityInfo.applicationInfo.flags) == 0)) {
                        bean.setRunning(true);
                    }

                    if (!appBeanList.contains(bean)) {
                        appBeanList.add(bean);
                    }
                }
            }
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return appBeanList;
    }

    /**
     * 判断是否可以跳转到拨号界面
     */
    public static boolean canResolveIntent(Context context) {
        try {
            String url = "tel:" + "10000";
            Intent intent = new Intent();
            intent.setData(Uri.parse(url));
            intent.setAction(Intent.ACTION_DIAL);
            // 是否可以处理跳转到拨号的 Intent
            return intent.resolveActivity(context.getPackageManager()) != null;
        } catch (Exception e) {
            PluginLog.error(e);
        }
        return false;
    }
}
