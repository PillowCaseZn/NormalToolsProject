package com.pillowcase.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.pillowcase.logger.LoggerUtils;
import com.pillowcase.logger.impl.ILoggerOperation;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-07-01 17:03
 * Description ： 网络状态判断
 */
public class NetUtils implements ILoggerOperation {
    private LoggerUtils mLoggerUtils;
    private Context mContext;

    public NetUtils(Context context) {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
        this.mContext = context;
    }

    /**
     * @return 网络是否连接成功
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean isNetConnect() {
        try {
            log("isNetConnect", "");
            ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo info = manager.getActiveNetworkInfo();
                return (info != null && info.isConnectedOrConnecting());
            }
        } catch (Exception e) {
            error(e, "isNetConnect");
        }
        return false;
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
