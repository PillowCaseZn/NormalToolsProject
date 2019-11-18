package com.pillowcase.normal.tools.only.sign.utils;

import android.os.Build;

/**
 * Author      : PillowCase
 * Create On   : 2019-11-18 11:04
 * Description : 手机系统
 */
public class SystemVersionUtils {
    private static SystemVersionUtils instance;
    private BaseLogger mLogger;

    private SystemVersionUtils() {
        if (mLogger == null) {
            mLogger = new BaseLogger(true, "OnlySignUtils-->SystemVersionUtils");
        }
    }

    public static SystemVersionUtils getInstance() {
        if (instance == null) {
            synchronized (SystemVersionUtils.class) {
                if (instance == null) {
                    instance = new SystemVersionUtils();
                }
            }
        }
        return instance;
    }

    /**
     * 判断系统版本是否大于AndroidM（26）
     *
     * @return
     */
    public boolean isVersionMoreThanM() {
        mLogger.log("isVersionMoreThanM", "Version : " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 26) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断系统版本是否大于AndroidQ（29）
     *
     * @return
     */
    public boolean isVersionMoreThanQ() {
        mLogger.log("isVersionMoreThanQ", "Version : " + Build.VERSION.SDK_INT);
        if (Build.VERSION.SDK_INT >= 29) {
            return true;
        } else {
            return false;
        }
    }
}
