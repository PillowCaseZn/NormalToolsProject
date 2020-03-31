package com.pillowcase.network.observer;

import com.pillowcase.network.model.NetworkType;

/**
 * Author      : PillowCase
 * Create On   : 2020-03-09 11:00
 * Description : 网络状态变化观察者
 */
public interface NetStateChangeObserver {

    void onNetDisconnected();

    void onNetConnected(NetworkType type);
}
