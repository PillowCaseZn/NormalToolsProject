package com.pillowcase.network.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;

import androidx.annotation.NonNull;

import com.pillowcase.network.model.NetworkType;
import com.pillowcase.network.observer.NetStateChangeObserver;
import com.pillowcase.network.utils.NetworkUtils;
import com.pillowcase.normal.tools.logger.LoggerUtils;
import com.pillowcase.normal.tools.logger.impl.ILoggerOperation;

import java.util.ArrayList;
import java.util.List;

/**
 * Author      : PillowCase
 * Create On   : 2020-03-09 11:02
 * Description :
 */
public class NetStateChangeReceiver extends BroadcastReceiver implements ILoggerOperation {
    private LoggerUtils mLoggerUtils;

    private static class InstanceHolder {
        private static final NetStateChangeReceiver INSTANCE = new NetStateChangeReceiver();
    }

    private List<NetStateChangeObserver> mObservers = new ArrayList<>();

    public NetStateChangeReceiver() {
        if (mLoggerUtils == null) {
            mLoggerUtils = new LoggerUtils(true, getClass().getSimpleName());
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            if (ConnectivityManager.CONNECTIVITY_ACTION.equals(intent.getAction())) {
                NetworkType networkType = NetworkUtils.getNetworkType(context);
                notifyObservers(networkType);
            }
        } catch (Exception e) {
            error(e, "onReceive");
        }
    }

    /**
     * 注册网络监听
     */
    public static void registerReceiver(@NonNull Context context) {
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        context.registerReceiver(InstanceHolder.INSTANCE, intentFilter);
    }

    /**
     * 取消网络监听
     */
    public static void unregisterReceiver(@NonNull Context context) {
        context.unregisterReceiver(InstanceHolder.INSTANCE);
    }

    /**
     * 注册网络变化Observer
     */
    public static void registerObserver(NetStateChangeObserver observer) {
        if (observer == null)
            return;
        if (!InstanceHolder.INSTANCE.mObservers.contains(observer)) {
            InstanceHolder.INSTANCE.mObservers.add(observer);
        }
    }

    /**
     * 取消网络变化Observer的注册
     */
    public static void unregisterObserver(NetStateChangeObserver observer) {
        if (observer == null)
            return;
        if (InstanceHolder.INSTANCE.mObservers == null)
            return;
        InstanceHolder.INSTANCE.mObservers.remove(observer);
    }

    /**
     * 通知所有的Observer网络状态变化
     */
    private void notifyObservers(NetworkType networkType) {
        if (networkType == NetworkType.NETWORK_WITHOUT_NETWORK) {
            for (NetStateChangeObserver observer : mObservers) {
                observer.onNetDisconnected();
            }
        } else {
            for (NetStateChangeObserver observer : mObservers) {
                observer.onNetConnected(networkType);
            }
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
