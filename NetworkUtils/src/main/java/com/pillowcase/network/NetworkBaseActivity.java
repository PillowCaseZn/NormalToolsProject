package com.pillowcase.network;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pillowcase.network.model.NetworkType;
import com.pillowcase.network.observer.NetStateChangeObserver;
import com.pillowcase.network.receiver.NetStateChangeReceiver;
import com.pillowcase.normal.tools.logger.LoggerUtils;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;

/**
 * Author      : PillowCase
 * Create On   : 2020-03-09 11:56
 * Description :
 */
public class NetworkBaseActivity extends AppCompatActivity implements NetStateChangeObserver, ILoggerOperation {
    private LoggerUtils mLoggerUtils;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.registerObserver(this);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (needRegisterNetworkChangeObserver()) {
            NetStateChangeReceiver.unregisterObserver(this);
        }
    }

    /**
     * 是否需要注册网络变化的Observer,如果不需要监听网络变化,则返回false;否则返回true.默认返回false
     */
    protected boolean needRegisterNetworkChangeObserver() {
        return false;
    }

    @Override
    public void onNetDisconnected() {
        log("onNetDisconnected", "网络断开");
    }

    @Override
    public void onNetConnected(NetworkType networkType) {
        log("onNetConnected", "NetworkType : " + networkType);
        try {

        } catch (Exception e) {
            error(e, "onNetConnected");
        }
    }

    @Override
    public void log(String s, Object o) {
        if (mLoggerUtils != null) {
            mLoggerUtils.log(s, o);
        }
    }

    @Override
    public void warn(String s, String s1) {
        if (mLoggerUtils != null) {
            mLoggerUtils.warn(s, s1);
        }
    }

    @Override
    public void error(Throwable throwable, String s) {
        if (mLoggerUtils != null) {
            mLoggerUtils.error(throwable, s);
        }
    }
}
