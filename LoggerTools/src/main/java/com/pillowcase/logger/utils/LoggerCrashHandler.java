package com.pillowcase.logger.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Process;

import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.logger.module.LoggerBorder;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Logger;

/**
 * Author      :  PillowCase
 * Created On  ： 2021-02-05 14:00
 * Description ： 用来处理在程序中未被捕获的异常。（如果程序中已经自己设置了try{}catch，则不会执行这个方法）。
 */
public class LoggerCrashHandler implements Thread.UncaughtExceptionHandler {
    private final Context mContext;
    /**
     * 是否有本地文件读写权限
     */
    private boolean isGrantedPermission = false;
    /**
     * 本地日志存储文件
     */
    private String crashFileName = "Crash-";

    private final String READ_EXTERNAL_STORAGE = Manifest.permission.READ_EXTERNAL_STORAGE;
    private final String WRITE_EXTERNAL_STORAGE = Manifest.permission.WRITE_EXTERNAL_STORAGE;

    public LoggerCrashHandler(Context context) {
        this.mContext = context;

        this.crashFileName += System.currentTimeMillis();

        // 判断系统版本，进而判断是否有本地文件读写权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PackageManager manager = this.mContext.getPackageManager();
            String packageName = this.mContext.getPackageName();
            if ((PackageManager.PERMISSION_GRANTED == manager.checkPermission(READ_EXTERNAL_STORAGE, packageName)) && (PackageManager.PERMISSION_GRANTED == manager.checkPermission(WRITE_EXTERNAL_STORAGE, packageName))) {
                isGrantedPermission = true;
            }
        } else {
            isGrantedPermission = true;
        }
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {
        String crashData = buildCrashData(thread, throwable);
        loggerCrashData(crashData);

        if (isGrantedPermission) {
            writeCrashData(buildFileCrashData(crashData));
        }

        // 系统默认异常处理
        Thread.UncaughtExceptionHandler handler = Thread.getDefaultUncaughtExceptionHandler();
        if (handler != null) {
            handler.uncaughtException(thread, throwable);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    @SuppressLint("SimpleDateFormat")
    private String buildCrashData(Thread thread, Throwable throwable) {
        StringBuilder builder = new StringBuilder(LoggerBorder.TOP_BORDER);

        builder.append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append("Time : ")
                .append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis())))
                .append(LoggerBorder.LINE_SEPARATOR)
                .append(LoggerBorder.MIDDLE_BORDER)
                .append(LoggerBorder.LINE_SEPARATOR);

        builder.append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append("Thread : ")
                .append(thread.getName())
                .append(LoggerBorder.LINE_SEPARATOR)
                .append(LoggerBorder.MIDDLE_BORDER)
                .append(LoggerBorder.LINE_SEPARATOR);

        builder.append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append(throwable)
                .append(LoggerBorder.LINE_SEPARATOR);

        StackTraceElement[] trace = throwable.getStackTrace();
        for (StackTraceElement element : trace) {
            builder.append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                    .append(LoggerBorder.DATA_SEPARATOR)
                    .append(element)
                    .append(LoggerBorder.LINE_SEPARATOR);
        }
        builder.append(LoggerBorder.BOTTOM_BORDER);
        return builder.toString();
    }

    private String buildFileCrashData(String message) {
        int index = message.indexOf(LoggerBorder.MIDDLE_BORDER);
        String[] data = new String[]{
                message.substring(0, index),
                message.substring(index)
        };

        StringBuilder builder = new StringBuilder(data[0]);

        builder.append(LoggerBorder.MIDDLE_BORDER)
                .append(LoggerBorder.LINE_SEPARATOR)
                //当前系统
                .append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append("OS version : ")
                .append(Build.VERSION.RELEASE).append("_").append(Build.VERSION.SDK_INT)
                .append(LoggerBorder.LINE_SEPARATOR)
                //制造商
                .append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append("Vendor : ")
                .append(Build.MANUFACTURER)
                .append(LoggerBorder.LINE_SEPARATOR)
                //手机型号
                .append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append("Model : ")
                .append(Build.MODEL)
                .append(LoggerBorder.LINE_SEPARATOR)
                //CPU架构
                .append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append("CPU ABI : ")
                .append(Build.CPU_ABI)
                .append(LoggerBorder.LINE_SEPARATOR)
                //AppName
                .append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append("AppName : ")
                .append(this.mContext.getApplicationInfo().loadLabel(this.mContext.getPackageManager()))
                .append(LoggerBorder.LINE_SEPARATOR)
                //包名
                .append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                .append("PackageName : ")
                .append(this.mContext.getPackageName())
                .append(LoggerBorder.LINE_SEPARATOR);

        try {
            PackageInfo info = this.mContext.getPackageManager().getPackageInfo(this.mContext.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (info != null) {
                builder.append(LoggerBorder.HORIZONTAL_DOUBLE_LINE)
                        .append("Version : ")
                        .append(info.versionCode)
                        .append("_")
                        .append(info.versionName)
                        .append(LoggerBorder.LINE_SEPARATOR);
            }
        } catch (Exception e) {
            LoggerUtils.getInstance().error(e);
        }

        return builder.append(data[1]).toString();
    }

    private void loggerCrashData(String message) {
        Logger logger = Logger.getLogger("Crash");
        if (message.contains(LoggerBorder.LINE_SEPARATOR)) {
            for (String line : message.split(LoggerBorder.LINE_SEPARATOR)) {
                logger.warning(line);
            }
        } else {
            logger.warning(message);
        }
    }

    /**
     * 日志写入文件
     *
     * @param crashData 奔溃信息
     */
    private void writeCrashData(String crashData) {
        FileOutputStream stream = null;
        BufferedWriter writer = null;
        try {
            stream = this.mContext.openFileOutput(crashFileName, Context.MODE_PRIVATE);
            if (stream != null) {
                writer = new BufferedWriter(new OutputStreamWriter(stream));
                writer.write(crashData);
            }
        } catch (Exception e) {
            LoggerUtils.getInstance().error(e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
                if (stream != null) {
                    stream.close();
                }
            } catch (Exception e) {
                LoggerUtils.getInstance().error(e);
            }
        }
    }
}
