package com.pillowcase.utils;

import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;

import com.pillowcase.logger.LoggerUtils;

/**
 * Author      :  PillowCase
 * Created On  ： 2020-07-01 17:03
 * Description ： 网络状态判断
 */
public class NetUtils {
    private LoggerUtils mLoggerUtils;
    private Context mContext;

    public NetUtils(Context context) {
        if (mLoggerUtils == null) {
            mLoggerUtils = LoggerUtils.getInstance();
        }
        this.mContext = context;
    }

    /**
     * @return 网络是否连接成功
     */
    @TargetApi(Build.VERSION_CODES.M)
    public boolean isNetConnect() {
        try {
            mLoggerUtils.log("isNetConnect", "");
            ConnectivityManager manager = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (manager != null) {
                NetworkInfo info = manager.getActiveNetworkInfo();
                return (info != null && info.isConnectedOrConnecting());
            }
        } catch (Exception e) {
            mLoggerUtils.error("isNetConnect", e);
        }
        return false;
    }
}
