package com.pillowcase.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;

import com.pillowcase.logger.impl.ILoggerOperation;
import com.pillowcase.utils.modules.InstallApp;

import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Author      : PillowCase
 * Create On   : 2020-07-29 10:43
 * Description : 获取设备已安装APP的信息
 */
public class AppInstallUtils implements ILoggerOperation {
    private static final AppInstallUtils ourInstance = new AppInstallUtils();
    private LoggerUtils mLoggerUtils;

    public static AppInstallUtils getInstance() {
        return ourInstance;
    }

    private AppInstallUtils() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(false, getClass().getSimpleName());
        }
    }

    /**
     * 获取已安装的APP信息
     *
     * @param context
     */
    public List<InstallApp> getInstallAppInfo(Context context) {
        List<InstallApp> resultList = new ArrayList<>();
        try {

            PackageManager manager = context.getPackageManager();
            if (manager != null) {
                Intent intent = new Intent(Intent.ACTION_MAIN, null);
                intent.addCategory(Intent.CATEGORY_LAUNCHER);
                List<ResolveInfo> resolveInfoList = manager.queryIntentActivities(intent, 0);
                Collections.sort(resolveInfoList, new ResolveInfo.DisplayNameComparator(manager));

                for (ResolveInfo info : resolveInfoList) {
                    InstallApp app = new InstallApp();
                    app.setAppName(String.valueOf(info.loadLabel(manager)));
                    app.setPackageName(info.activityInfo.packageName);
                    app.setApplicationName(info.activityInfo.applicationInfo.className);
                    if (((ApplicationInfo.FLAG_SYSTEM & info.activityInfo.applicationInfo.flags) == 0)
                            && ((ApplicationInfo.FLAG_UPDATED_SYSTEM_APP & info.activityInfo.applicationInfo.flags) == 0)
                            && ((ApplicationInfo.FLAG_STOPPED & info.activityInfo.applicationInfo.flags) == 0)) {
                        app.setRunning(true);
                    }
                    log("getInstallAppInfo", "InstallApp : " + app);

                    if (!resultList.contains(app)) {
                        resultList.add(app);
                    }
                }
            }
        } catch (Exception e) {
            error(e, "getInstallAppInfo");
        }
        return resultList;
    }

    /**
     * 获取后台运行的线程信息
     *
     * @param context
     */
    public void getRunningProcess(Context context) {
        try {
            ProcessBuilder builder = new ProcessBuilder("/system/bin/top", "-n", "1");
            builder.directory(new File("/system/bin/"));
            builder.redirectErrorStream(true);
            Process process = builder.start();
            InputStream stream = process.getInputStream();
            byte[] bytes = new byte[1024];
            while (stream.read(bytes) != -1) {
                String data = new String(bytes);
                Pattern pattern = Pattern.compile("^([a-zA-Z_][a-zA-Z0-9_]*)+([.][a-zA-Z_][a-zA-Z0-9_]*)+$");
                Matcher matcher = pattern.matcher(data);
                log("getRunningProcess", data);
            }
            stream.close();
        } catch (Exception e) {
            error(e, "getRunningProcess");
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
