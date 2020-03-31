package com.pillowcase.network;

import android.app.Application;

import com.pillowcase.network.receiver.NetStateChangeReceiver;

/**
 * Author      : PillowCase
 * Create On   : 2020-03-09 11:55
 * Description :
 */
public class NetworkApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // 注册BroadcastReceiver
        NetStateChangeReceiver.registerReceiver(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        // 取消BroadcastReceiver注册
        NetStateChangeReceiver.unregisterReceiver(this);
    }
}
